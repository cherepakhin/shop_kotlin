package ru.perm.v.shopkotlin.mapper

import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.entity.ProductEntity

object MapperProduct {
    fun mapFromDtoToEntity(dto: ProductDTO) = ProductEntity(
        dto.n,
        dto.name,
        dto.groupDtoN
    )
    fun mapFromEntityToDto(entity: ProductEntity) = ProductDTO(
        entity.n,
        entity.name,
        entity.groupProductN
    )
}