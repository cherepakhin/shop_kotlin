package ru.perm.v.shopkotlin.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.perm.v.shopkotlin.repository.ProductRepository
import ru.perm.v.shopkotlin.service.GroupProductService

@DataJpaTest
class ProductServiceIntegrationTest {
    @Autowired
    lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var groupProductService: GroupProductService

    @Test
    fun forDEMO() {
        val productService = ProductServiceImpl(productRepository, groupProductService)
        assertEquals(9, productService.productRepository.findAllByOrderByNAsc().size)
    }

}