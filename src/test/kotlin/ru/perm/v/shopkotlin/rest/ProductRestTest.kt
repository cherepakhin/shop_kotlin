package ru.perm.v.shopkotlin.rest

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.*
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.service.ProductService

@ExtendWith(MockitoExtension::class)
internal class ProductRestTest {

    @Mock
    private lateinit var mockProductService: ProductService

    @InjectMocks
    private lateinit var productRest: ProductRest

    @Test
    fun getCountOfProductNames() {
        val count = 5L
        Mockito.`when`(mockProductService.getCountOfProductNames()).thenReturn(count)
        assertEquals(5L, productRest.getCountOfProductNames())
    }

    @Test
    fun getByN() {
        val N = 100L
        Mockito.`when`(mockProductService.getByN(N))
            .thenReturn(ProductDTO(100L, "NAME", 10L))
        val dto = productRest.getByN(100L)

        assertEquals(ProductDTO(100L, "NAME", 10L), dto)
    }

    @Test
    fun getAll() {
        val productDTO1 = ProductDTO(1L, "NAME_1", 10L)
        val productDTO2 = ProductDTO(2L, "NAME_2", 20L)
        Mockito.`when`(mockProductService.getAll())
            .thenReturn(listOf(productDTO1, productDTO2))

        val dtos = productRest.getAll()

        assertEquals(2, dtos.size)
        assertEquals(productDTO1, dtos.get(0))
        assertEquals(productDTO2, dtos.get(1))
    }

    @Test
    fun validateProductDTO() {
        val productDTO = ProductDTO(-1L, "", -1L)
        var exceptMessage = ""
        try {
            productRest.validate(productDTO)
        } catch (excp: Exception) {
            exceptMessage = excp.message!!
        }
        assertEquals(
            "ProductDTO(n=-1, name='', groupDtoN=-1) has errors: Field 'name' in ProductDTO is empty\n",
            exceptMessage
        )
    }

    @Test
    fun create() {
        val productDTO = ProductDTO(1L, "NAME_1", 10L)
        val createdProductDTO = ProductDTO(1L, "CREATED_NAME_1", 10L)
        Mockito.`when`(mockProductService.create(productDTO))
            .thenReturn(createdProductDTO)

        val dto = productRest.create(productDTO)

        assertEquals(createdProductDTO, dto)
    }

    @Test
    fun createWhenNotValidProductDTO() {
        val productDTO = ProductDTO(1L, "", 10L)

        val exception = assertThrows<Exception> { productRest.create(productDTO) }

        assertEquals(
            "ProductDTO(n=1, name='', groupDtoN=10) has errors: Field 'name' in ProductDTO is empty\n",
            exception.message
        )
    }

    @Test
    fun updateWhenNotValidProductDTO() {
        val N = 1L
        val productDTO = ProductDTO(N, "", 10L)

        val exception = assertThrows<Exception> { productRest.update(N, productDTO) }

        assertEquals(
            "ProductDTO(n=1, name='', groupDtoN=10) has errors: Field 'name' in ProductDTO is empty\n",
            exception.message
        )

        Mockito.verify(mockProductService, never()).update(any())
    }

    @Test
    fun updateWhenValidProductDTODoesNotThrow() {
        val N = 1L
        val productDTO = ProductDTO(N, "NAME_1", 10L)
        assertDoesNotThrow { productRest.update(N, productDTO) }
    }

    @Test
    fun update() {
        val N = 1L

        val productDTO = ProductDTO(N, "NAME_1", 10L)
        val updatedProductDTO = ProductDTO(1L, "CREATED_NAME_1", 10L)

        Mockito.`when`(mockProductService.update(productDTO))
            .thenReturn(updatedProductDTO)

        val returnedProductDTO = productRest.update(N, productDTO)

        assertEquals(updatedProductDTO, returnedProductDTO)
    }

    @Test
    fun deleteById() {
        val ID = 100L

        productRest.deleteById(ID)

        verify(mockProductService, times(1)).delete(ID)
    }

    @Test
    fun deleteByNotExistId() {
        val ID = 100L
        doThrow(Exception("MESSAGE")).`when`(mockProductService).delete(ID)

        val exception = assertThrows<Exception> { productRest.deleteById(ID) }

        assertEquals(
            "MESSAGE",
            exception.message
        )
    }
}