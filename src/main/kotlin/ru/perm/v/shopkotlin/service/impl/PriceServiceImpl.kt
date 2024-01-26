package ru.perm.v.shopkotlin.service.impl

import org.springframework.stereotype.Service
import ru.perm.v.shopkotlin.dto.PriceDTO
import ru.perm.v.shopkotlin.repository.PriceRepository

@Service
class PriceServiceImpl(val priceRepository: PriceRepository) {
    fun findAllByOrderByNAsc(): List<PriceDTO> {
        //TODO: implement
        return listOf()
    }
}