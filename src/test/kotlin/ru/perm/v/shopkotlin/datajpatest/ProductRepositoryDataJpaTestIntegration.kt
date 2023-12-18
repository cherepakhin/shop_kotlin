package ru.perm.v.shopkotlin.datajpatest

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.perm.v.shopkotlin.repository.ProductRepository
import kotlin.test.assertEquals

/**
 * Еще один вариант интеграционных тестов.
 * Тестируется работа ТОЛЬКО с базой данных, И ТОЛЬКО ProductRepository
 * с использованием @DataJpaTest.
 */
@DataJpaTest
@Disabled
class ProductRepositoryDataJpaTestIntegration {
    @Autowired
    lateinit var productRepository: ProductRepository;

    @Test
    fun getAll() {
        val products = productRepository.findAll()
        assertEquals(9, products.size);
    }

    @Test
    fun getByDslFilterFromRepository() {
        Assertions.assertEquals(9, productRepository.findAllByOrderByNAsc().size)
    }
}