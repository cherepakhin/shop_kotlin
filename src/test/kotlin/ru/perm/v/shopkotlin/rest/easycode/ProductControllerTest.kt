package ru.perm.v.shopkotlin.rest.easycode

import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.rest.ProductRest
import ru.perm.v.shopkotlin.service.ProductService

@ExtendWith(MockitoExtension::class)
class ProductControllerTest {

    @Mock
    private lateinit var productService: ProductService

    @InjectMocks
    private lateinit var productRest: ProductRest

    @Test
    fun testGetByN() {
// answer from EasyCode:
//        @RunWith(MockitoJUnitRunner::class)
//        class ProductControllerTest {
//
//            @Mock
//            private lateinit var productService: ProductService
//
//            @InjectMocks
//            private lateinit var productController: ProductController
//
//            @Test
//            fun testGetByN() {
//                // Arrange
//                val n = 1L
//                val productDTO = ProductDTO(1L, &quot;Product&quot;, 10.0, &quot;Product Description&quot;)
//                Mockito.when(productService.getByN(n)).thenReturn(productDTO)
//
//                // Act
//                val result = productController.getByN(n)
//
//                // Assert
//                Mockito.verify(productService, Mockito.times(1)).getByN(n)
//                Assert.assertEquals(productDTO, result)
//            }
//        }

        // Arrange
        val n = 1L
        val productDTO = ProductDTO(1L, "Product", -1L)
        `when`(productService.getByN(n)).thenReturn(productDTO)

        // Act
        val result = productRest.getByN(n)

        // Assert
        Mockito.verify(productService, Mockito.times(1)).getByN(n)
        Assert.assertEquals(productDTO, result)
    }

    @Test
    fun testNextRequestToEasyCodeGetByN() {
        // Arrange
        val n = 1L
        val productDTO = ProductDTO(1L, "Product", 10L)
        `when`(productService.getByN(n)).thenReturn(productDTO)
        // Act
        val result = productRest.getByN(n)
        // Assert
        Mockito.verify(productService, Mockito.times(1)).getByN(n)
        Assert.assertEquals(productDTO, result)
    }
}

