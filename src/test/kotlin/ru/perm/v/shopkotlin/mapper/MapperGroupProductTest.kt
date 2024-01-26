package ru.perm.v.shopkotlin.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.entity.GroupProductEntity

class MapperGroupProductTest {

    @Test
    fun mapFromDtoToEntity() {
        val groupProductDTO = GroupProductDTO(1L, "NAME_1")

        val entity = MapperGroupProduct.mapFromDtoToEntity(groupProductDTO)

        assertEquals(GroupProductEntity(1L, "NAME_1"), entity)
    }

    @Test
    fun mapFromEntityToDto() {
        val entity = GroupProductEntity(1L, "NAME_1")

        val groupProductDTO = MapperGroupProduct.mapFromEntityToDto(entity)

        assertEquals(GroupProductDTO(1L, "NAME_1"), groupProductDTO)
    }
}