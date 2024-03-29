package ru.perm.v.shopkotlin.rest.codeium

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.rest.ProductRest
import ru.perm.v.shopkotlin.service.ProductService
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class ProductRestTest {

    @Test
    fun getByN() {
        val N = 100L

        val mockProductService = mock<ProductService>()

        Mockito.`when`(mockProductService.getByN(N))
            .thenReturn(ProductDTO(100L, "NAME", 10L))

        val productRest = ProductRest(mockProductService)

        Assertions.assertEquals(ProductDTO(100L, "NAME", 10L), productRest.getByN(N))
    }

    @Test
    fun getAll() {
        val mockProductService = mock<ProductService>()
        Mockito.`when`(mockProductService.getAll()).thenReturn(
            listOf(
                ProductDTO(1L, "NAME_1", 10L),
                ProductDTO(2L, "NAME_2", 20L)
            )
        )

        val productRest = ProductRest(mockProductService)

        Assertions.assertEquals(2, productRest.getAll().size)
    }

    @Test
    fun deleteById() {
        val ID = 100L
        val mockProductService = mock<ProductService>()
        val productRest = ProductRest(mockProductService)

        productRest.deleteById(ID)

        Mockito.verify(mockProductService, times(1)).delete(ID)
    }

    @Test
    fun validate() {
        val mockProductService = mock<ProductService>()
        val productRest = ProductRest(mockProductService)
        val productDTO = ProductDTO(-1L, "", -1L)
        val exception = assertThrows<Exception> { productRest.validate(productDTO) }
        assertEquals(
            "ProductDTO(n=-1, name='', groupDtoN=-1) has errors: Field 'name' in ProductDTO is empty\n",
            exception.message
        )
    }

    @Test
    fun update() {
        val mockProductService = mock<ProductService>()
        val productRest = ProductRest(mockProductService)
        val productDTO = ProductDTO(100L, "NEW NAME", 10L)

        doReturn(ProductDTO(100L, "NEW NAME", 10L)).`when`(mockProductService).update(productDTO)

        val returnedProductDTO = productRest.update(-1, productDTO)

        assertEquals(ProductDTO(100L, "NEW NAME", 10L), returnedProductDTO)
    }
}