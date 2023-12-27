package ru.perm.v.shopkotlin.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.service.GroupProductService
import ru.perm.v.shopkotlin.service.ProductService


@ExtendWith(SpringExtension::class)
@WebMvcTest(GroupProductRest::class)
class GroupProductRestMockMvcTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    lateinit var mockedGroupProductService: GroupProductService //need for constructor GroupProductRest

    @MockBean
    lateinit var productService: ProductService //need for constructor GroupProductRest

    @Test
    fun testEchoMessage() {
        val mes = mockMvc.perform(
            MockMvcRequestBuilders.get("/group_product/echo/ECHO_MESSAGE")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
        assertEquals("ECHO_MESSAGE", mes.response.contentAsString)
    }

    @Test
    fun getByNormalId() {
        `when`(mockedGroupProductService.getByN(1L)).thenReturn(GroupProductDTO(1L, "NAME_1"))

        val jsonGroup = mockMvc.perform(
            MockMvcRequestBuilders.get("/group_product/1")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn().response.contentAsString
        val gr = ObjectMapper().readValue(jsonGroup, GroupProductDTO::class.java)
        assertEquals(GroupProductDTO(1L, "NAME_1"), gr)
    }

    @Test
    fun getByNegativeId() {
        `when`(mockedGroupProductService.getByN(-1L)).thenThrow(Exception("ERROR"))
        assertThrows<Exception> {
            mockMvc.perform(
                MockMvcRequestBuilders.get("/group_product/-1")
                    .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().`is`(500))
        }
    }

    @Test
    fun getAll() {
        val gr1 = GroupProductDTO(1L, "NAME_1")
        val gr2 = GroupProductDTO(2L, "NAME_2")
        `when`(mockedGroupProductService.findAllByOrderByNAsc()).thenReturn(listOf(gr1, gr2))

        val jsonGroup = mockMvc.perform(
            MockMvcRequestBuilders.get("/group_product/")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn().response.contentAsString
        val receivedGroups = ObjectMapper().readValue<List<GroupProductDTO>>(jsonGroup)
        assertEquals(2, receivedGroups.size)
        assertEquals(gr1, receivedGroups.get(0))
        assertEquals(gr2, receivedGroups.get(1))
    }

    @Test
    fun getByNotExistId() {
        `when`(mockedGroupProductService.getByN(100L)).thenThrow(Exception("NOT FOUND"))

        val thrown = assertThrows<Exception> {
            mockMvc.perform(
                MockMvcRequestBuilders.get("/group_product/100")
                    .accept(MediaType.APPLICATION_JSON)
            )
        }
        assertEquals(
            "Request processing failed; nested exception is java.lang.Exception: NOT FOUND",
            thrown.message
        )
    }

// OK
//        mockMvc.get("/group_product/1") {
//            contentType = MediaType.APPLICATION_JSON
//            accept = MediaType.APPLICATION_JSON
//        }.andExpect {
//            status { MockMvcResultMatchers.status().isOk }
////            content { contentType(MediaType.APPLICATION_JSON) }
////            content { json("{}") }
//        }

    //        mockMvc.get("/group_product/100") {
//            contentType = MediaType.APPLICATION_JSON
//            accept = MediaType.APPLICATION_JSON
//        }.andExpect {
//            status { MockMvcResultMatchers.status().is5xxServerError }
////            content { contentType(MediaType.APPLICATION_JSON) }
////            content { json("{}") }
//        }
    @Test
    fun getByNotExistId_OtherVariant() {
        `when`(mockedGroupProductService.getByN(100L)).thenThrow(Exception("NOT FOUND"))

        try {
            mockMvc.get("/group_product/100") {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
            }

        } catch (excp: Exception) {
            assertEquals("Request processing failed; nested exception is java.lang.Exception: NOT FOUND", excp.message)
        }
    }

    @Test
    fun deleteForNotExist() {
        val N = 100L
        `when`(mockedGroupProductService.existsByN(N)).thenReturn(false)
        val excpt = assertThrows<Exception> {
            mockMvc.perform(
                MockMvcRequestBuilders.delete("/group_product/" + N)
            )
        }
        val ERROR_MESSAGE = "Request processing failed; nested exception is java.lang.Exception: Group product not found with id=100"
        assertEquals(ERROR_MESSAGE, excpt.message)
        // Another way verify
        assertTrue(excpt.message?.equals(ERROR_MESSAGE) ?: false)
    }
}