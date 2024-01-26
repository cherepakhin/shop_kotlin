package ru.perm.v.shopkotlin.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.entity.ProductEntity

class MapperProductTest {

    @Test
    fun mapFromDtoToEntity() {
        val N = 1L
        val NAME = "NAME_1"
        val GROUP_N = 1L
        val productDTO = ProductDTO(N, NAME, GROUP_N,)

        val entity = MapperProduct.mapFromDtoToEntity(productDTO)

        assertEquals(ProductEntity(N, NAME, GROUP_N), entity)

    }

    @Test
    fun mapFromEntityToDto() {
        val N = 1L
        val NAME = "NAME_1"
        val GROUP_N = 1L
        val entity = ProductEntity(N, NAME, GROUP_N)

        val productDTO = MapperProduct.mapFromEntityToDto(entity)

        assertEquals(ProductDTO(N, NAME, GROUP_N), productDTO)
    }
}