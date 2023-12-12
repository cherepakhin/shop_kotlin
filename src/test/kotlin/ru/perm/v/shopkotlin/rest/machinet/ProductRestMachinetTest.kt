package ru.perm.v.shopkotlin.rest.machinet

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.rest.ProductRest
import ru.perm.v.shopkotlin.service.ProductService
import kotlin.test.assertEquals

@WebMvcTest(ProductRest::class)
class ProductRestMachinetTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    private lateinit var productService: ProductService

    @Test
    fun `getByN should return a product by ID`() {
        val productId = 1L
        val productDTO = ProductDTO(productId, "Test Product", 10L)

        `when`(productService.getByN(productId)).thenReturn(productDTO)

        mockMvc.perform(get("/product/{n}", productId))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.n").value(productDTO.n))
            .andExpect(jsonPath("$.name").value(productDTO.name))
            .andExpect(jsonPath("$.groupDtoN").value(productDTO.groupDtoN))

        verify(productService).getByN(productId)
    }

    @Test
    fun `getAll should return all products`() {
        val product1 = ProductDTO(1L, "Product 1", 10L)
        val product2 = ProductDTO(2L, "Product 2", 20L)
        val productList = listOf(product1, product2)

        `when`(productService.getAll()).thenReturn(productList)

        mockMvc.perform(get("/product/"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].n").value(product1.n))
            .andExpect(jsonPath("$[0].name").value(product1.name))
            .andExpect(jsonPath("$[1].n").value(product2.n))
            .andExpect(jsonPath("$[1].name").value(product2.name))

        verify(productService).getAll()
    }

    @Test
    fun `update should update a product`() {
        val productId = 1L
        val productDTO = ProductDTO(productId, "Test Product", 10L)

        `when`(productService.update(productDTO)).thenReturn(productDTO)

        val mapper = ObjectMapper()
        val result = mockMvc.perform(
            post("/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(productDTO))
        )
            .andReturn()
//        assertEquals("", result.response.contentAsString)
        val receivedProductDTO = mapper.readValue<ProductDTO>(result.response.contentAsString)
        assertEquals(productDTO, receivedProductDTO)
    }

    @Test
    fun `deleteById should delete a product by ID`() {
        val productId = 1L

        mockMvc.perform(delete("/product/{n}", productId))
            .andExpect(status().isOk)

        verify(productService).delete(productId)
    }
}