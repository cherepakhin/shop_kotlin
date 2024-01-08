package ru.perm.v.shopkotlin.rest.bitotest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.rest.GroupProductRest
import ru.perm.v.shopkotlin.service.GroupProductService
import ru.perm.v.shopkotlin.service.ProductService
import java.util.*

/**
 * Generated Bito. Test disabled.
 * ONLY for example work Bito. The tests don't work. Can be used as an example, skeleton, etc.
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(GroupProductRest::class)
@Disabled
class GroupProductRestBitoCodeTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    private lateinit var groupProductService: GroupProductService

    @MockBean
    private lateinit var productService: ProductService

    val mapper = ObjectMapper().registerModule(KotlinModule())

    @Test
    fun testEchoMessage_Positive() {
        val message = "Hello"
        mockMvc.perform(get("/group_product/echo/{mes}", message))
            .andExpect(status().isOk)
            .andExpect(content().string(message))
    }

    @Test
    fun testCreate_Positive() {
        val groupProductDTO = GroupProductDTO()
        groupProductDTO.name = "Group 1"

        `when`(groupProductService.create(groupProductDTO)).thenReturn(groupProductDTO)

        val mes = mockMvc.perform(
            post("/group_product")
                .contentType("application/json")
                .content("{\n" +
                        "  \"n\": 8,\n" +
                        "  \"name\": \"TEST GROUP_PRODUCT JSON\",\n" +
                        "  \"parentN\": 1,\n" +
                        "  \"haveChilds\": false\n" +
                        "}")
        )
            .andExpect(status().isOk)
            .andReturn()

        assertEquals("", mes.response.contentAsString)
//        val createdGroupProduct = mapper.readValue<ProductDTO>(mes.response.contentAsString)

//        assertEquals(GroupProductDTO(100,"Group 100").toString(), createdGroupProduct)
//            .andExpect(jsonPath("$.n").value(100))
    }

    @Test
    fun testCreate_InvalidInput() {
        mockMvc.perform(
            post("/group_product")
                .contentType("application/json")
                .content("{\"name\":\"\"}")
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun testGetById_Positive() {
        val groupProductDTO = GroupProductDTO()
        groupProductDTO.name = "Group 1"

        `when`(groupProductService.getByN(anyLong())).thenReturn(groupProductDTO)

        mockMvc.perform(get("/group_product/{n}", 1L))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Group 1"))
    }

    @Test
    fun testGetById_InvalidId() {
        assertThrows<Exception> {
            mockMvc.perform(get("/group_product/{n}", -1L))
                .andExpect(status().`is`(500))
        }
    }

    @Test
    fun testAll_Positive() {
        val groupProductDTO1 = GroupProductDTO()
        groupProductDTO1.name = "Group 1"
        val groupProductDTO2 = GroupProductDTO()
        groupProductDTO2.name = "Group 2"
        val groupProductDTOs = Arrays.asList(groupProductDTO1, groupProductDTO2)

        `when`(groupProductService.findAllByOrderByNAsc()).thenReturn(groupProductDTOs)

        mockMvc.perform(get("/group_product/"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value("Group 1"))
            .andExpect(jsonPath("$[1].name").value("Group 2"))
    }

    @Test
    fun testAll_NoGroupsFound() {
        `when`(groupProductService.findAllByOrderByNAsc()).thenReturn(emptyList())

        mockMvc.perform(get("/group_product/"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun testFindByName_Positive() {
        val groupProductDTO1 = GroupProductDTO()
        groupProductDTO1.name = "Group 1"
        val groupProductDTO2 = GroupProductDTO()
        groupProductDTO2.name = "Group 2"
        val groupProductDTOs = Arrays.asList(groupProductDTO1, groupProductDTO2)

        `when`(groupProductService.findByNameContaining(anyString())).thenReturn(groupProductDTOs)

        mockMvc.perform(
            get("/group_product/find")
                .param("name", "Group")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value("Group 1"))
            .andExpect(jsonPath("$[1].name").value("Group 2"))
    }

    @Test
    fun testFindByName_NoGroupsFound() {
        `when`(groupProductService.findByNameContaining(anyString())).thenReturn(emptyList())

        mockMvc.perform(
            get("/group_product/find")
                .param("name", "Group")
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun testGetSubGroups_Positive() {
        val groupProductDTO1 = GroupProductDTO()
        groupProductDTO1.name = "Group 1"
        val groupProductDTO2 = GroupProductDTO()
        groupProductDTO2.name = "Group 2"
        val groupProductDTOs = Arrays.asList(groupProductDTO1, groupProductDTO2)

        `when`(groupProductService.getSubGroups(anyLong())).thenReturn(groupProductDTOs)

        mockMvc.perform(get("/group_product/{n}/subgroups", 1L))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value("Group 1"))
            .andExpect(jsonPath("$[1].name").value("Group 2"))
    }

    @Test
    fun testGetSubGroups_InvalidId() {
        mockMvc.perform(get("/group_product/{n}/subgroups", -1L))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun testDeleteByN_Positive() {
        mockMvc.perform(delete("/group_product/{n}", 1L))
            .andExpect(status().isOk)
    }

    @Test
    fun testDeleteByN_GroupNotFound() {
        mockMvc.perform(delete("/group_product/{n}", 1L))
            .andExpect(status().isNotFound)
    }

    @Test
    fun testDeleteByN_HaveSubGroups() {
        `when`(groupProductService.getSubGroups(anyLong())).thenReturn(Arrays.asList(GroupProductDTO()))

        mockMvc.perform(delete("/group_product/{n}", 1L))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun testDeleteByN_HaveProducts() {
        `when`(productService.getByGroupProductN(anyLong())).thenReturn(Arrays.asList(ProductDTO()))

        mockMvc.perform(delete("/group_product/{n}", 1L))
            .andExpect(status().isBadRequest)
    }
}