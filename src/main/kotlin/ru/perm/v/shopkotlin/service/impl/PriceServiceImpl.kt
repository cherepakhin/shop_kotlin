package ru.perm.v.shopkotlin.service.impl

import org.springframework.stereotype.Service
import ru.perm.v.shopkotlin.dto.PriceDTO
import ru.perm.v.shopkotlin.mapper.MapperPrice
import ru.perm.v.shopkotlin.repository.PriceRepository

@Service
class PriceServiceImpl(val priceRepository: PriceRepository) {
    fun findAllByOrderByNAsc(): List<PriceDTO> {
        val dtos = mutableListOf<PriceDTO>()
        priceRepository.findAllByOrderByNAsc().forEach({ dtos.add(MapperPrice.mapFromEntityToDto(it)) })
//      Other variants - OK
//        priceRepository.findAllByOrderByNAsc().stream().map { MapperPrice.mapFromEntityToDto(it) }.forEach { dtos.add(it) }
        return dtos
    }
}