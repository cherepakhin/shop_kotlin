package ru.perm.v.shopkotlin.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class PriceDTOTest {

    @Test
    fun testEquals() {
        val product100 = ProductDTO(100L, "NAME_100", 1L)
        val priceTypeDTO = PriceTypeDTO(1L, "PRICE_1")

        val priceDTO1 = PriceDTO(1L, product100, priceTypeDTO, BigDecimal.valueOf(10.00))
        val priceDTO2 = PriceDTO(1L, product100, priceTypeDTO, BigDecimal.valueOf(10.00))

        assertEquals(priceDTO1, priceDTO2)
        assertEquals(priceDTO2, priceDTO1)
    }
}