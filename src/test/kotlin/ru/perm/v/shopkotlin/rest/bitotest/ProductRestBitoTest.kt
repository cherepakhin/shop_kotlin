package ru.perm.v.shopkotlin.rest.bitotest

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.cache.annotation.EnableCaching
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.rest.ProductRest
import ru.perm.v.shopkotlin.service.ProductService


/**
 * Сенерировано Bito
 * немного подправлял, но в целом нормально работает.
 */
@SpringBootTest
@EnableCaching
@ExtendWith(SpringExtension::class)
class ProductRestBitoTest {
    @MockBean
    private lateinit var productService: ProductService
    private lateinit var productRest: ProductRest

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        productRest = ProductRest(productService)
    }

    @get:Test
    val countOfProductNames_PositiveCase: Unit
        get() {
            Mockito.`when`(productService!!.getCountOfProductNames()).thenReturn(10L)
            val count = productRest!!.getCountOfProductNames()
            Assertions.assertEquals(10L, count)
        }

    @Test
    fun echoMessage_PositiveCase() {
        val message = "Hello"
        val result = productRest.echoMessage(message)
        Assertions.assertEquals(message, result)
    }

    @Test
    fun create_PositiveCase() {
        val productDTO = ProductDTO()
        // Set productDTO properties
        Mockito.`when`(productService.create(productDTO)).thenReturn(productDTO)
        val result = productRest.create(productDTO)
        Assertions.assertEquals(productDTO, result)
    }

    // Set productDTO properties

    @Test
    fun update_PositiveCase() {
        val n = 1L
        val productDTO = ProductDTO()
        productDTO.n = n
        // Set productDTO properties
        Mockito.`when`(productService.update(productDTO)).thenReturn(productDTO)
        val result = productRest.update(n, productDTO)
        Assertions.assertEquals(productDTO, result)
    }

    @Test
    fun deleteById_PositiveCase() {
        val n = 1L
        Assertions.assertDoesNotThrow {
            productRest.deleteById(
                n
            )
        }
    }

    @Test
    fun validate_NegativeCase() {
        val productDTO = ProductDTO()
        productDTO.name = ""
        // Set invalid productDTO properties
        Assertions.assertThrows(Exception::class.java) {
            productRest.validate(
                productDTO
            )
        }
    }
}