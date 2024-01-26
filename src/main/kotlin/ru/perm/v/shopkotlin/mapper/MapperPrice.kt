package ru.perm.v.shopkotlin.mapper

import ru.perm.v.shopkotlin.dto.PriceDTO
import ru.perm.v.shopkotlin.entity.PriceEntity
import ru.perm.v.shopkotlin.entity.PriceTypeEntity
import ru.perm.v.shopkotlin.entity.ProductEntity

object MapperPrice {
    val mapperPriceType = MapperPriceType
    val mapperProduct = MapperProduct
    fun mapFromDtoToEntity(dto: PriceDTO): PriceEntity {
        val productEntity = mapperProduct.mapFromDtoToEntity(dto.productDTO)
        val priceTypeEntity = mapperPriceType.mapFromDtoToEntity(dto.priceTypeDTO)

        return PriceEntity(
            dto.n,
            dto.productDTO.n,
            dto.priceTypeDTO.n,
            dto.price
        )
    }

    fun mapFromEntityToDto(entity: PriceEntity) : PriceDTO = PriceDTO(
        entity.n,
        mapperProduct.mapFromEntityToDto(ProductEntity(entity.productN)),
        mapperPriceType.mapFromEntityToDto(PriceTypeEntity(entity.priceTypeN, "")), //TODO: NAME PRICETYPE???
        entity.price
    )
}