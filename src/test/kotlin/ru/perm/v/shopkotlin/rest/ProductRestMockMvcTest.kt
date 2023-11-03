package ru.perm.v.shopkotlin.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.service.ProductService

@ExtendWith(SpringExtension::class)
@WebMvcTest(ProductRest::class)
class ProductRestMockMvcTest(@Autowired private val mockMvc: MockMvc) {

    // Used "@MockBean" for REMOVE ERROR for MockMvc
    // "... available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: ..."
    @MockBean
    private lateinit var mockProductService: ProductService

    val mapper = ObjectMapper().registerModule(KotlinModule())

    @Test
    fun echoMessage() {
        val mes = mockMvc.perform(
            MockMvcRequestBuilders.get("/product/echo/ECHO_MESSAGE")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
        assertEquals("ECHO_MESSAGE", mes.response.contentAsString)
    }

    @Test
    fun create() {
        val product = ProductDTO(10L, "NAME_10", -1L)
        doReturn(product).`when`(mockProductService).create(product)

        val mes = mockMvc.perform(
            MockMvcRequestBuilders
                .post("/product")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assertEquals("{\"n\":10,\"name\":\"NAME_10\",\"groupDtoN\":-1}", mes.response.contentAsString)

        val receivedProductDTO = mapper.readValue<ProductDTO>(mes.response.contentAsString)
        assertEquals(product, receivedProductDTO)
    }

    @Test
    fun getByN() {
        val N = 10L
        val product = ProductDTO(N, "NAME_10", -1L)
        doReturn(product).`when`(mockProductService).getByN(N)

        val mes = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/product/" + N)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assertEquals("{\"n\":10,\"name\":\"NAME_10\",\"groupDtoN\":-1}", mes.response.contentAsString)

        val receivedProductDTO = mapper.readValue<ProductDTO>(mes.response.contentAsString)
        assertEquals(product, receivedProductDTO)
    }

    @Test
    fun getAll() {
        val N10 = 10L
        val N11 = 11L
        val PRODUCT_10 = ProductDTO(N10, "NAME_10", -1L)
        val PRODUCT_11 = ProductDTO(N11, "NAME_11", -1L)

        val products = listOf<ProductDTO>(PRODUCT_10, PRODUCT_11)
        doReturn(products).`when`(mockProductService).getAll()

        val mes = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/product/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(products))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val receivedDTOs = mapper.readValue<List<ProductDTO>>(mes.response.contentAsString)

        assertEquals(2, receivedDTOs.size)
        assertEquals(PRODUCT_10, receivedDTOs.get(0))
        assertEquals(PRODUCT_11, receivedDTOs.get(1))
    }

    @Test
    fun update() {
        val N = 10L
        val product = ProductDTO(N, "NAME_10", -1L)
        doReturn(product).`when`(mockProductService).update(product)

        val mes = mockMvc.perform(
            MockMvcRequestBuilders
                .post("/product/" + N)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        assertEquals("{\"n\":10,\"name\":\"NAME_10\",\"groupDtoN\":-1}", mes.response.contentAsString)

        val receivedProductDTO = mapper.readValue<ProductDTO>(mes.response.contentAsString)
        assertEquals(product, receivedProductDTO)
    }

    @Test
    fun deleteById() {
        val N = 10L

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/product/" + N)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        verify(mockProductService, times(1)).delete(N)
    }

    @Test
    fun deleteByNotExistId() {
        val N = 10L

        `when`(mockProductService.delete(N)).thenThrow(Exception("ERROR"))

        assertThrows<Exception> {
            mockMvc.perform(
                MockMvcRequestBuilders
                    .delete("/product/" + N)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andReturn()
        }
    }

}