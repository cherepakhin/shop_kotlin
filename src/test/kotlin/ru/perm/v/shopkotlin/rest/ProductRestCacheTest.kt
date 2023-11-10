package ru.perm.v.shopkotlin.rest

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.service.ProductService
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductRestCacheTest(@Autowired val productRest: ProductRest) {
    @MockBean
    lateinit var productService: ProductService

    @Test
    fun getByN() {
        val N = 1L;
        val product1 = ProductDTO(N, "NAME_1", -1)

        Mockito.`when`(productService.getByN(N)).thenReturn(product1)
        // call 3 times
        productRest.getByN(N)
        productRest.getByN(N)
        val dto = productRest.getByN(N)

        assertEquals(N, dto.n)
        // but productService.getByN(N) was called only 1 time
        verify(productService, times(1)).getByN(N)
    }


//    @Test
//    fun getAll() {
//        val product1 = ProductDTO(1, "NAME_1", -1)
//        val product2 = ProductDTO(2, "NAME_2", -1)
//
//        Mockito.`when`(productService.getAll()).thenReturn(listOf(product1, product2))
//
//        productRest.getAll()
//        productRest.getAll()
//        val products = productRest.getAll()
//
//        assertEquals(2, products.size)
//        verify(productService, times(3)).getAll()
//    }
}