package ru.perm.v.shopkotlin.rest

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.service.GroupProductService
import kotlin.test.assertEquals


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GroupProductRestCacheTest(@Autowired val groupProductRest: GroupProductRest) {

    @MockBean
    lateinit var groupProductService: GroupProductService

    @Test
    fun checkWorkCacheOnGetAll() {
        val gr1 = GroupProductDTO(1L, "NAME_1")
        val gr2 = GroupProductDTO(2L, "NAME_2")
        Mockito.`when`(groupProductService.findAllByOrderByNAsc()).thenReturn(listOf(gr1, gr2))

        groupProductRest.all()
        groupProductRest.all()
        val groups = groupProductRest.all()

        assertEquals(2, groups.size)

        verify(groupProductService, times(1)).findAllByOrderByNAsc()
    }

    @Test
    fun checkWorkCacheOnGetById() {
        val ID = 1L
        val gr1 = GroupProductDTO(ID, "NAME_1")
        Mockito.`when`(groupProductService.getByN(ID)).thenReturn(gr1)

        // do 3 times GET to REST for check work cache
        groupProductRest.getById(ID)
        groupProductRest.getById(ID)
        val group = groupProductRest.getById(ID)

        assertEquals(ID, group.n)

        // is cashed DTO
        verify(groupProductService, times(1)).getByN(ID)
    }

    @Disabled // Work only in manual mode
    @Test
    fun checkWorkCacheOnGetSubGroups() {
        val ID = 1L
        val SUB_GROUP_10 = GroupProductDTO(10L, "NAME_10")
        val SUB_GROUP_11 = GroupProductDTO(11L, "NAME_11")

        Mockito.`when`(groupProductService.existsByN(ID)).thenReturn(true)

        Mockito.`when`(groupProductService.getSubGroups(ID)).thenReturn(listOf(SUB_GROUP_10, SUB_GROUP_11))

        // do 3 request to service
        groupProductRest.getSubGroups(ID)
        groupProductRest.getSubGroups(ID)
        val subGroups = groupProductRest.getSubGroups(ID)

        assertEquals(2, subGroups.size)
        assertEquals(SUB_GROUP_10, subGroups.get(0))
        assertEquals(SUB_GROUP_11, subGroups.get(1))

        // groupProductRest.getSubGroups(ID) вызван 3 раза,
        // но groupProductService .getSubGroups(ID) только 1 раз
        verify(groupProductService, times(1)).getSubGroups(ID)
    }
}