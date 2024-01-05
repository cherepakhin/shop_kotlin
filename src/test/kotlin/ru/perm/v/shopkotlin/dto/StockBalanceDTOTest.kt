package ru.perm.v.shopkotlin.dto

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class StockBalanceDTOTest {

    @Test
    fun testEquals() {
        val stockDTO1 = StockDTO(20L, "NMAE_20")
        val product1 = ProductDTO(100L, "NAME_100", 1L)

        val stockBalance1 = StockBalanceDTO(stockDTO1, product1, 10L)
        val stockBalance2 = StockBalanceDTO(stockDTO1, product1, 10L)

        assertEquals(stockBalance1, stockBalance2)
    }
}