package ru.perm.v.shopkotlin.mapper

import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.entity.GroupProductEntity

object MapperGroupProduct {
    fun mapFromDtoToEntity(dto: GroupProductDTO) = GroupProductEntity(
        dto.n,
        dto.name
    )
    fun mapFromEntityToDto(entity: GroupProductEntity) = GroupProductDTO(
        entity.n,
        entity.name
    )
}