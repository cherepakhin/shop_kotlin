package ru.perm.v.shopkotlin.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import ru.perm.v.shopkotlin.entity.PriceEntity
import ru.perm.v.shopkotlin.repository.PriceRepository
import java.math.BigDecimal

class PriceServiceImplMockTest {

    @Mock
    private  var repository:PriceRepository = mock(PriceRepository::class.java)

    @Test
    fun findAllByOrderByNAsc() {
        val priceService = PriceServiceImpl(repository)
        val price1 = PriceEntity(1, 1, 1, BigDecimal.valueOf(10.00))
        val price2 = PriceEntity(2, 1, 2, BigDecimal.valueOf(20.00))
        `when` (repository.findAllByOrderByNAsc()).thenReturn(listOf(price1, price2))

        val prices = priceService.findAllByOrderByNAsc()

        assertEquals(2, prices.size)
        assertEquals(BigDecimal.valueOf(10.00), prices[0].price)
        assertEquals(BigDecimal.valueOf(20.00), prices[1].price)
    }
}