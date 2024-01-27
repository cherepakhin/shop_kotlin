package ru.perm.v.shopkotlin.mapper

import ru.perm.v.shopkotlin.dto.PriceDTO
import ru.perm.v.shopkotlin.entity.PriceEntity
import ru.perm.v.shopkotlin.entity.PriceTypeEntity

object MapperPrice {
    val mapperPriceType = MapperPriceType
    val mapperProduct = MapperProduct
    fun mapFromDtoToEntity(dto: PriceDTO): PriceEntity {
        val priceTypeEntity = mapperPriceType.mapFromDtoToEntity(dto.priceTypeDTO)

        return PriceEntity(
            dto.n,
            dto.productN,
            dto.priceTypeDTO.n,
            dto.price
        )
    }

    fun mapFromEntityToDto(entity: PriceEntity) : PriceDTO = PriceDTO(
        entity.n,
        entity.productN,
        mapperPriceType.mapFromEntityToDto(PriceTypeEntity(entity.priceTypeN, "")), //TODO: NAME PRICETYPE???
        entity.price
    )
}