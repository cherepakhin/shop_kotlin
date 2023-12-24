package ru.perm.v.shopkotlin.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class PriceDTOTest {

    @Test
    fun testEquals() {
        val product100 = ProductDTO(100L,"NAME_100", 1L)
        val priceDTO1 = PriceDTO(product100, BigDecimal.valueOf(10.00))
        val priceDTO2 = PriceDTO(product100, BigDecimal.valueOf(10.00))
        assertEquals(priceDTO1, priceDTO2)
    }
}