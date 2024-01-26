package ru.perm.v.shopkotlin.mapper

import ru.perm.v.shopkotlin.dto.PriceTypeDTO
import ru.perm.v.shopkotlin.entity.PriceTypeEntity

object MapperPriceType {
    fun mapFromDtoToEntity(priceTypeDTO: PriceTypeDTO) = PriceTypeEntity(
        priceTypeDTO.n,
        priceTypeDTO.name
    )
    fun mapFromEntityToDto(priceTypeEntity: PriceTypeEntity) = PriceTypeDTO(
        priceTypeEntity.n,
        priceTypeEntity.name
    )
}