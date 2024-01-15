package ru.perm.v.shopkotlin.datajpatest

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.perm.v.shopkotlin.repository.PriceTypeRepository
import kotlin.test.assertEquals

/**
 * Тестируется работа ТОЛЬКО с базой данных, И ТОЛЬКО pricetypeRepository
 * с использованием @DataJpaTest.
 */
@DataJpaTest
class PricetypeRepositoryDataJpaTestIntegration {
    @Autowired
    lateinit var pricetypeRepository: PriceTypeRepository;

    @Test
    fun getAll() {
        val priceTypes = pricetypeRepository.findAll()

        assertEquals(2, priceTypes.size);
    }

    @Test
    fun getByDslFilterFromRepository() {
        Assertions.assertEquals(2, pricetypeRepository.findAllByOrderByNAsc().size)
    }
}