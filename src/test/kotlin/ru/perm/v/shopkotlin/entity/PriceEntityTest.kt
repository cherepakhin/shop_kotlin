package ru.perm.v.shopkotlin.entity

import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class PriceEntityTest {

    @Test
    fun testToString() {
        val idProduct100 = 100L
        val priceEntity = PriceEntity(1L, idProduct100, 1L, BigDecimal.valueOf(10.00))

        assertEquals("PriceEntity(n=1, productN=100, priceTypeN=1, price=10.0)", priceEntity.toString())
    }

    @Test
    fun testEquals() {
        val idProduct100 = 100L
        val priceEntity1 = PriceEntity(1L, idProduct100, 1L, BigDecimal.valueOf(10.00))
        val priceEntity2 = PriceEntity(1L, idProduct100, 1L, BigDecimal.valueOf(10.00))

        assertEquals(priceEntity1, priceEntity2)
    }

}