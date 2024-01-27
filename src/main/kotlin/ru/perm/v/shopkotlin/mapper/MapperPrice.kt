package ru.perm.v.shopkotlin.mapper

import ru.perm.v.shopkotlin.dto.PriceDTO
import ru.perm.v.shopkotlin.entity.PriceEntity

object MapperPrice {
    val mapperPriceType = MapperPriceType
    val mapperProduct = MapperProduct
    fun mapFromDtoToEntity(dto: PriceDTO): PriceEntity {

        return PriceEntity(
            dto.n,
            dto.productN,
            dto.priceTypeN,
            dto.price
        )
    }

    fun mapFromEntityToDto(entity: PriceEntity) : PriceDTO = PriceDTO(
        entity.n,
        entity.productN,
        entity.priceTypeN,
        entity.price
    )
}