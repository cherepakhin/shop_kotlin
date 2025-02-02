package ru.perm.v.shopkotlin.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.perm.v.shopkotlin.dto.PriceTypeDTO
import ru.perm.v.shopkotlin.repository.PriceTypeRepository

@DataJpaTest
class PriceTypeServiceImplIntegrationTest {
    @Autowired
    lateinit var priceTypeRepository: PriceTypeRepository


    @Test
    fun create() {
        val priceTypeService = PriceTypeServiceImpl(priceTypeRepository)
        assertEquals(PriceTypeDTO(1, "test"), priceTypeService.create(PriceTypeDTO(1, "test")))
    }

    @Test
    fun findAllByOrderByNAsc() {
        val priceTypeService = PriceTypeServiceImpl(priceTypeRepository)
        assertEquals(2, priceTypeService.findAllByOrderByNAsc().size)
    }

    @Test
    fun getByN() {
        val priceTypeService = PriceTypeServiceImpl(priceTypeRepository)
        assertEquals(PriceTypeDTO(1L, "Normal price"), priceTypeService.getByN(1L))
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2])
    fun getByWithParamN(N: Long) {
        val priceTypeService = PriceTypeServiceImpl(priceTypeRepository)

        assertEquals(N, priceTypeService.getByN(N).n)
    }

    @ParameterizedTest
    @CsvSource("1,'Normal price'", "2,'Discount price'")
    fun getByWithParamFromCasv(N: Long, NAME:String) {
        val priceTypeService = PriceTypeServiceImpl(priceTypeRepository)

        assertEquals(NAME, priceTypeService.getByN(N).name)
    }
}