package ru.perm.v.shopkotlin.rest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.service.GroupProductService
import ru.perm.v.shopkotlin.service.ProductService

/**
 * Tests generated Bito (ChatGPT)
 */
@SpringBootTest
class GroupProductRestBitoTest {

    @MockBean
    private lateinit var groupProductService: GroupProductService

    @MockBean
    private lateinit var productService: ProductService

    private lateinit var groupProductRest: GroupProductRest

    @BeforeEach
    fun setUp() {
        groupProductRest = GroupProductRest(groupProductService, productService)
    }

    @Test
    fun testEchoMessage() {
        val message = "Hello"
        val result = groupProductRest.echoMessage(message)
        assertEquals(message, result)
    }

    @Test
    fun testCreate_ValidDTO_ReturnsDTO() {
        val groupProductDTO = GroupProductDTO(1, "Group", 0L, false)

        `when`(groupProductService.create(groupProductDTO)).thenReturn(groupProductDTO)
        val result = groupProductRest.create(groupProductDTO)

        assertEquals(groupProductDTO, result)
        verify(groupProductService, times(1)).create(groupProductDTO)
    }

    @Test
    fun testCreate_InvalidDTO_ThrowsException() {
        val groupProductDTO = GroupProductDTO(1, "", 0L, false)

        assertThrows(Exception::class.java) {
            groupProductRest.create(groupProductDTO)
        }
        verify(groupProductService, never()).create(groupProductDTO)
    }

    @Test
    fun testGetById_ValidId_ReturnsDTO() {
        val groupId = 1L
        val groupProductDTO = GroupProductDTO(groupId, "Group", 0L, false)

        `when`(groupProductService.getByN(groupId)).thenReturn(groupProductDTO)

        val result = groupProductRest.getById(groupId)

        assertEquals(groupProductDTO, result)
        verify(groupProductService, times(1)).getByN(groupId)
    }

    @Test
    fun testGetById_InvalidId_ThrowsException() {
        val groupId = -1L

        assertThrows(Exception::class.java) {
            groupProductRest.getById(groupId)
        }
        verify(groupProductService, never()).getByN(groupId)
    }

    @Test
    fun testAll_ReturnsListOfDTOs() {
        val groupProductDTOs = listOf(
            GroupProductDTO(1, "Group1", 0L, false),
            GroupProductDTO(2, "Group2", 0L, false)
        )

        `when`(groupProductService.findAllByOrderByNAsc()).thenReturn(groupProductDTOs)

        val result = groupProductRest.all()

        assertEquals(groupProductDTOs, result)
        verify(groupProductService, times(1)).findAllByOrderByNAsc()
    }

    @Test
    fun testClearCache_ReturnsClearedMessage() {
        val result = groupProductRest.clearCache()

        assertEquals("cleared", result)
    }

    @Test
    fun testFindByName_ValidName_ReturnsListOfDTOs() {
        val groupName = "Group"
        val groupProductDTOs = listOf(
            GroupProductDTO(1, "Group1", 0L, false),
            GroupProductDTO(2, "Group2", 0L, false)
        )

        `when`(groupProductService.findByNameContaining(groupName)).thenReturn(groupProductDTOs)

        val result = groupProductRest.findByName(groupName)

        assertEquals(groupProductDTOs, result)
        verify(groupProductService, times(1)).findByNameContaining(groupName)
    }

    @Test
    fun testGetSubGroups_ValidId_ReturnsListOfDTOs() {
        val groupId = 1L
        val subGroups = listOf(
            GroupProductDTO(2, "SubGroup1", groupId, false),
            GroupProductDTO(3, "SubGroup2", groupId, false)
        )

        `when`(groupProductService.existsByN(groupId)).thenReturn(true)
        `when`(groupProductService.getSubGroups(groupId)).thenReturn(subGroups)

        val result = groupProductRest.getSubGroups(groupId)

        assertEquals(subGroups, result)
        verify(groupProductService, times(1)).existsByN(groupId)
        verify(groupProductService, times(1)).getSubGroups(groupId)
    }

    @Test
    fun testGetSubGroups_InvalidId_ThrowsException() {
        val groupId = -1L

        `when`(groupProductService.existsByN(groupId)).thenReturn(false)

        assertThrows(Exception::class.java) {
            groupProductRest.getSubGroups(groupId)
        }
        verify(groupProductService, times(1)).existsByN(groupId)
        verify(groupProductService, never()).getSubGroups(groupId)
    }

    @Test
    fun testDeleteByN_ValidId_DeletesGroup() {
        val groupId = 1L

        `when`(groupProductService.existsByN(groupId)).thenReturn(true)
        `when`(groupProductService.getSubGroups(groupId)).thenReturn(emptyList())
        `when`(productService.getByGroupProductN(groupId)).thenReturn(emptyList())

        groupProductRest.deleteByN(groupId)

        verify(groupProductService, times(1)).existsByN(groupId)
        verify(groupProductService, times(1)).getSubGroups(groupId)
        verify(productService, times(1)).getByGroupProductN(groupId)
        verify(groupProductService, times(1)).deleteByN(groupId)
    }

    @Test
    fun testDeleteByN_InvalidId_ThrowsException() {
        val groupId = -1L

        `when`(groupProductService.existsByN(groupId)).thenReturn(false)

        assertThrows(Exception::class.java) {
            groupProductRest.deleteByN(groupId)
        }
        verify(groupProductService, times(1)).existsByN(groupId)
        verify(groupProductService, never()).getSubGroups(groupId)
        verify(productService, never()).getByGroupProductN(groupId)
        verify(groupProductService, never()).deleteByN(groupId)
    }

    // Add more test cases for other methods

}