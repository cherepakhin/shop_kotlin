package ru.perm.v.shopkotlin.service.impl

import org.springframework.stereotype.Service
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.service.ProductService
import ru.perm.v.shopkotlin.service.ReceiverProductExtFromQueueService

/**
 *  Сервис предназначен для приема данных о продуктах из очереди Kafka
 */
@Service
class ReceiverProductExtFromQueueServiceImpl(var productService: ProductService) : ReceiverProductExtFromQueueService {
    override fun getFromQueue(): List<ProductDTO> {
        return emptyList()
//        val products = productService.getAll()
    }
}