package ru.perm.v.shopkotlin.rest.easycode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.rest.ProductRest
import ru.perm.v.shopkotlin.service.ProductService


class ProductRestEasyCodeTest {
    @Mock
    private lateinit var productService: ProductService

    @InjectMocks
    private lateinit var productRest: ProductRest

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        productRest = ProductRest(productService)
    }

    @Test
    fun testGetProductById() {
        // Arrange
        val productN = 1L
        val productDto = ProductDTO()
        productDto.n = productN
        Mockito.`when`(productService.getByN(productN)).thenReturn(productDto)

        // Act
        val result: ProductDTO = productRest.getByN(productN)

        // Assert
        assertEquals(productDto, result)
        verify(productService, times(1)).getByN(productN)
    }

    @Test
    fun testCreateProduct() {
        // Arrange
        val productN = 1L
        val productDto = ProductDTO()
        productDto.n = productN
        productDto.name = "Test Product"
        Mockito.`when`(productService.create(productDto)).thenReturn(productDto)

        // Act
        val result = productRest.create(productDto)

        // Assert
        assertEquals(productDto, result)
        Mockito.verify(productService, times(1)).create(productDto)
    }

    @Test
    fun testUpdateProduct() {
        // Arrange
        val productN = 1L
        val productDto = ProductDTO()
        productDto.n = productN
        productDto.name = "Test Product"

        Mockito.`when`(productService.update(productDto)).thenReturn(productDto)

        // Act
        val result = productRest.update(productN, productDto)

        // Assert
        assertEquals(productDto, result)
        Mockito.verify(productService, times(1)).update(productDto)
    }

    @Test
    fun testDeleteProduct() {
        // Arrange
        val productId = 1L
        Mockito.doNothing().`when`(productService).delete(productId)

        // Act
        productRest.deleteById(productId)

        // Assert
        Mockito.verify(productService).delete(productId)
    }
}
