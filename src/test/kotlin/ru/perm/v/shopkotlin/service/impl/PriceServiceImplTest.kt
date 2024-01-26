package ru.perm.v.shopkotlin.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import ru.perm.v.shopkotlin.entity.PriceEntity
import ru.perm.v.shopkotlin.repository.PriceRepository
import java.math.BigDecimal

class PriceServiceImplTest {

    @Mock
    val repository: PriceRepository = mock(PriceRepository::class.java)

    @Test
    fun findAllByOrderByNAsc() {
        val priceService = PriceServiceImpl(repository)
        `when`(repository.findAllByOrderByNAsc()).thenReturn(
            listOf(
                PriceEntity(100L, 1L, 1L, BigDecimal.valueOf(10.00)),
                PriceEntity(200L, 1L, 2L, BigDecimal.valueOf(20.00))
            )
        )

        val prices = priceService.findAllByOrderByNAsc()

        assertEquals(2, prices.size)
    }
}