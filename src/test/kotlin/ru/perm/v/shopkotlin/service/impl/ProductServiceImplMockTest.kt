package ru.perm.v.shopkotlin.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.entity.ProductEntity
import ru.perm.v.shopkotlin.repository.ProductRepository
import ru.perm.v.shopkotlin.service.GroupProductService
import java.util.*

internal class ProductServiceImplMockTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var groupProductService: GroupProductService

    @InjectMocks
    // @InjectMocks САМ создает экземпляр service
    // c "инъектированным" MOCK productRepository, GroupProductService, EntityManager
    // EntityManager для inject находит в контексте
    private lateinit var productService: ProductServiceImpl


    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun create() {
        val GROUP_PRODUCT_N = 100L
        val PRODUCT_NEXT_N = 1000L

        Mockito.`when`(groupProductService.existsByN(GROUP_PRODUCT_N)).thenReturn(true)
        Mockito.`when`(productRepository.getNextN()).thenReturn(PRODUCT_NEXT_N)
        val newProductWithCalcN = ProductEntity(PRODUCT_NEXT_N, "NAME", GROUP_PRODUCT_N)
        Mockito.`when`(productRepository.save(ProductEntity(PRODUCT_NEXT_N,"NAME", GROUP_PRODUCT_N))).thenReturn(newProductWithCalcN)

        val savedProduct = productService.create(ProductDTO(-1L, "NAME", GROUP_PRODUCT_N))

        assertEquals(ProductDTO(PRODUCT_NEXT_N, "NAME", GROUP_PRODUCT_N), savedProduct)
        Mockito.verify(productRepository, times(1)).save(ProductEntity(PRODUCT_NEXT_N, "NAME", GROUP_PRODUCT_N))
    }

    @Test
    fun createWithNotExistGroupN() {
        val GROUP_PRODUCT_N = 100L

        Mockito.`when`(groupProductService.existsByN(GROUP_PRODUCT_N)).thenReturn(false)

        val excpt = assertThrows<Exception> {
            productService.create(ProductDTO(-1L, "NAME", GROUP_PRODUCT_N))
        }
        assertEquals("GroupProduct with n=100 not exist", excpt.message)
    }

    @Test
    fun getByGroupProductN() {
        val GROUP_PRODUCT_N = 100L
        val productEntity10 = ProductEntity(10L, "NAME_10", GROUP_PRODUCT_N)
        val productEntity20 = ProductEntity(20L, "NAME_20", GROUP_PRODUCT_N)
        val products = listOf(productEntity10, productEntity20)
        Mockito.`when`(productRepository.findAllByGroupProductNOrderByNAsc(GROUP_PRODUCT_N)).thenReturn(products)

        val ret = productService.getByGroupProductN(GROUP_PRODUCT_N)

        assertEquals(2, ret.size)
        Mockito.verify(productRepository, times(1)).findAllByGroupProductNOrderByNAsc(GROUP_PRODUCT_N)
    }

    @Test
    fun existByN() {
        val PRODUCT_N = 1000L
        Mockito.`when`(productRepository.findById(PRODUCT_N))
            .thenReturn(Optional.of(ProductEntity(PRODUCT_N, "", -100)))

        assertTrue(productService.existByN(PRODUCT_N))
        Mockito.verify(productRepository, times(1)).findById(PRODUCT_N)
    }

    @Test
    fun updateName() {
        val GROUP_PRODUCT_N = 100L
        val PRODUCT_N = 1000L

        Mockito.`when`(groupProductService.existsByN(GROUP_PRODUCT_N)).thenReturn(true)
        Mockito.`when`(productRepository.findById(PRODUCT_N))
            .thenReturn(Optional.of(ProductEntity(PRODUCT_N, "NAME", GROUP_PRODUCT_N)))

        val savedProductForMock = ProductEntity(PRODUCT_N, "NEW_NAME", GROUP_PRODUCT_N)
        val product = ProductEntity(PRODUCT_N, "NEW_NAME", GROUP_PRODUCT_N)
        Mockito.`when`(productRepository.save(product)).thenReturn(savedProductForMock)

        val updatedProduct = productService.update(ProductDTO(PRODUCT_N, "NEW_NAME", GROUP_PRODUCT_N))

        assertEquals("NEW_NAME", updatedProduct.name)
        Mockito.verify(productRepository, times(1)).save(product)
    }

    @Test
    fun updateForNotExistProduct() {
        val GROUP_PRODUCT_N = 100L
        val PRODUCT_N = 1000L

        Mockito.`when`(groupProductService.existsByN(GROUP_PRODUCT_N)).thenReturn(true)
        Mockito.`when`(productRepository.findById(PRODUCT_N))
            .thenReturn(Optional.empty())

        val excpt = assertThrows<Exception> {
            productService.update(ProductDTO(PRODUCT_N, "NEW_NAME", GROUP_PRODUCT_N))
        }

        assertEquals("Product with n=1000 not exist", excpt.message)
    }

    @Test
    fun updateForNotExistGroupProduct() {
        val GROUP_PRODUCT_N = 100L
        val PRODUCT_N = 1000L

        Mockito.`when`(groupProductService.existsByN(GROUP_PRODUCT_N)).thenReturn(false)

        val excpt = assertThrows<Exception> {
            productService.update(ProductDTO(PRODUCT_N, "NEW_NAME", GROUP_PRODUCT_N))
        }

        assertEquals("GroupProduct with n=100 not exist", excpt.message)
    }

    @Test
    fun deleteForNotExistProduct() {
        val PRODUCT_N = 1000L

        Mockito.`when`(productRepository.findById(PRODUCT_N))
            .thenReturn(Optional.empty())

        val excpt = assertThrows<Exception> {
            productService.delete(PRODUCT_N)
        }

        assertEquals("Product with n=1000 not exist", excpt.message)
    }

    @Test
    fun deleteForExistProduct() {
        val PRODUCT_N = 1000L

        Mockito.`when`(productRepository.findById(PRODUCT_N))
            .thenReturn(Optional.of(ProductEntity(PRODUCT_N, "", -1)))

        productService.delete(PRODUCT_N)

        Mockito.verify(productRepository, times(1)).deleteById(PRODUCT_N)
    }

    @Test
    fun getByNs() {
        val nn = listOf(10L, 20L)
        Mockito.`when`(productRepository.findAllById(listOf(10L, 20L)))
            .thenReturn(
                listOf(
                    ProductEntity(10L, "", -1),
                    ProductEntity(20L, "", -1)
                )
            )
        val products = productService.getByNs(nn)
        assertEquals(2, products.size)
        assertEquals(10, products.get(0).n)
        assertEquals(20, products.get(1).n)
    }

    @Test
    fun getByIdsForNotExist() {
        val nn = listOf(10L, 20L)
        Mockito.`when`(productRepository.findAllById(listOf(10L, 20L)))
            .thenReturn(listOf())
        val products = productService.getByNs(nn)
        assertEquals(0, products.size)
    }

    @Test
    fun getByN() {
        val PRODUCT_N = 100L
        val GROUP_PRODUCT_N = 10L
        Mockito.`when`(productRepository.findById(PRODUCT_N))
            .thenReturn(Optional.of(ProductEntity(PRODUCT_N, "NAME", GROUP_PRODUCT_N)))
        val product = productService.getByN(PRODUCT_N)
        assertEquals(ProductDTO(100, "NAME", GROUP_PRODUCT_N), product)
    }

    @Test
    fun getByNotExistN() {
        val PRODUCT_N = 100L
        Mockito.`when`(productRepository.findById(PRODUCT_N))
            .thenReturn(Optional.empty())
        val excpt = assertThrows<Exception> {
            productService.getByN(PRODUCT_N)
        }
        assertEquals("Product not found with id=100", excpt.message)
    }

    @Test
    fun getAll() {
        val product10 = ProductEntity(10L, "", -1)
        val product20 = ProductEntity(20L, "", -1)
        Mockito.`when`(productRepository.findAllByOrderByNAsc())
            .thenReturn(listOf(product10, product20))

        val products = productService.getAll()

        assertEquals(2, products.size)
        assertEquals(ProductDTO(10L, "", -1), products.get(0))
        assertEquals(ProductDTO(20L, "", -1), products.get(1))
    }

    @Test
    fun getEmptyAll() {
        Mockito.`when`(productRepository.findAllByOrderByNAsc())
            .thenReturn(listOf())

        val products = productService.getAll()

        assertEquals(0, products.size)
    }

    @Test
    fun getCountOfProductNames() {
        val COUNT_NAMES = 100L;
        Mockito.`when`(productRepository.getCountOfProductNames())
            .thenReturn(COUNT_NAMES)

        assertEquals(COUNT_NAMES, productService.getCountOfProductNames())
    }

}