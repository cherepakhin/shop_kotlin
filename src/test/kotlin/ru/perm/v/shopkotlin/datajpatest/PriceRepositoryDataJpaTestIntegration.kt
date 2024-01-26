package ru.perm.v.shopkotlin.datajpatest

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.perm.v.shopkotlin.repository.PriceRepository
import kotlin.test.assertEquals

/**
 * Еще один вариант интеграционных тестов.
 * Тестируется работа ТОЛЬКО с базой данных, И ТОЛЬКО ProductRepository
 * с использованием @DataJpaTest.
 */
@DataJpaTest
class PriceRepositoryDataJpaTestIntegration {
    @Autowired
    lateinit var priceRepository: PriceRepository

    @Test
    fun getAll() {
        val prices = priceRepository.findAll()

        assertEquals(18, prices.size);
    }

    @Test
    fun getByDslFilterFromRepository() {
        assertEquals(18, priceRepository.findAllByOrderByNAsc().size)
    }
}