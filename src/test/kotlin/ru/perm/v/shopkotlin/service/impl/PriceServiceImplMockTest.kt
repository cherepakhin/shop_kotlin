package ru.perm.v.shopkotlin.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import ru.perm.v.shopkotlin.entity.PriceEntity
import java.math.BigDecimal

internal class PriceServiceImplMockTest {
    @Mock
    private lateinit var repository: ru.perm.v.shopkotlin.repository.PriceRepository

    @Test
    fun findAllByOrderByNAsc() {
        val priceService = PriceServiceImpl(repository)
        val price1 = PriceEntity(1, 1, 1, BigDecimal.valueOf(10.00))
        val price2 = PriceEntity(2, 1, 2, BigDecimal.valueOf(20.00))
        `when` (repository.findAll()).thenReturn(listOf(price1, price2))

        val prices = priceService.findAllByOrderByNAsc()
        assertEquals(2, prices.size)
        assertEquals(BigDecimal.valueOf(10.00), prices[0].price)
        assertEquals(BigDecimal.valueOf(20.00), prices[1].price)

    }
}