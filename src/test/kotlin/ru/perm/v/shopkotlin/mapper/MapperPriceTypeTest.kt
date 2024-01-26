package ru.perm.v.shopkotlin.mapper

import org.junit.jupiter.api.Test
import ru.perm.v.shopkotlin.dto.PriceTypeDTO
import ru.perm.v.shopkotlin.entity.PriceTypeEntity
import kotlin.test.assertEquals

class MapperPriceTypeTest {

    @Test
    fun mapFromDtoToEntity() {
        val priceTypeDTO = PriceTypeDTO(1L, "PRICE_1")
        val priceTypeEntity = MapperPriceType.mapFromDtoToEntity(priceTypeDTO)

        assertEquals(PriceTypeEntity(1L, "PRICE_1"), priceTypeEntity)
    }

    @Test
    fun mapFromEntityToDto() {
        val priceTypeEntity = PriceTypeEntity(1L, "PRICE_1")
        val priceTypeDTO = MapperPriceType.mapFromEntityToDto(priceTypeEntity)

        assertEquals(PriceTypeDTO(1L, "PRICE_1"), priceTypeDTO)
    }
}