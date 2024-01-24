package ru.perm.v.shopkotlin.service

import ru.perm.v.shopkotlin.dto.ProductDTO

/**
 *  Сервис для приема данных о продуктах из очереди Kafka
 */

interface ReceiverProductExtFromQueueService {
    @Throws(Exception::class)
    fun getFromQueue(): List<ProductDTO>
}