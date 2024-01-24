package ru.perm.v.shopkotlin.service.impl

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.perm.v.shopkotlin.service.ProductService
import kotlin.test.assertEquals

internal class ReceiverProductExtFromQueueServiceImplMockTest {

    @Mock
    private lateinit var productService: ProductService

    @InjectMocks
    private lateinit var service: ReceiverProductExtFromQueueServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getFromQueue() {

        val receivedProductDTOs = service.getFromQueue()

        assertEquals(0, receivedProductDTOs.size)
    }
}