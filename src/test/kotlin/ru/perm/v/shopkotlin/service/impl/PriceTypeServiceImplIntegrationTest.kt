package ru.perm.v.shopkotlin.service.impl

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.perm.v.shopkotlin.dto.PriceTypeDTO
import ru.perm.v.shopkotlin.repository.PriceTypeRepository
import ru.perm.v.shopkotlin.repository.ProductRepository

@DataJpaTest
class PriceTypeServiceImplIntegrationTest {
    @Autowired
    lateinit var priceTypeRepository: PriceTypeRepository


    @Test
    internal fun create() {
        val priceTypeService = PriceTypeServiceImpl(priceTypeRepository)
        assertEquals(PriceTypeDTO(1, "test"), priceTypeService.create(PriceTypeDTO(1, "test")))
    }

    @Test
    fun findAllByOrderByNAsc() {
        val priceTypeService = PriceTypeServiceImpl(priceTypeRepository)
        assertEquals(2, priceTypeService.findAllByOrderByNAsc().size)
    }
}