package ru.perm.v.shopkotlin.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.perm.v.shopkotlin.repository.PriceTypeRepository

@DataJpaTest
class PriceTypeServiceIntegrationTest {
    @Autowired
    lateinit var priceTypeRepository: PriceTypeRepository

    @Test
    internal fun findAllByOrderByNAsc() {
        assertEquals(2, priceTypeRepository.findAllByOrderByNAsc().size)
    }
}