package ru.perm.v.shopkotlin.mapper

import org.junit.jupiter.api.Test
import ru.perm.v.shopkotlin.dto.PriceDTO
import ru.perm.v.shopkotlin.dto.PriceTypeDTO
import ru.perm.v.shopkotlin.entity.PriceEntity
import java.math.BigDecimal
import kotlin.test.assertEquals

class MapperPriceTest {
    @Test
    fun mapFromDtoToEntity() {
        val priceTypeDTO = PriceTypeDTO(1L, "PRICE_1")
        val priceDTO = PriceDTO(1L, 100L, priceTypeDTO.n, BigDecimal.valueOf(10.00))

        val priceEntity = MapperPrice.mapFromDtoToEntity(priceDTO)

        assertEquals(PriceEntity(1L, 100L, 1L, BigDecimal.valueOf(10.00)),
            priceEntity)
    }

    @Test
    fun mapFromEntityToDto() {
        val priceEntity = PriceEntity(1L, 100L, 1L, BigDecimal.valueOf(10.00))
        val priceDTO = MapperPrice.mapFromEntityToDto(priceEntity)

        assertEquals(PriceDTO(1L, 100L, 1L, BigDecimal.valueOf(10.00)), priceDTO)
    }
}