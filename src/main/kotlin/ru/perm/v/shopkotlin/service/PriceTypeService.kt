package ru.perm.v.shopkotlin.service

import ru.perm.v.shopkotlin.dto.PriceTypeDTO

interface PriceTypeService {

    @Throws(Exception::class)
    fun create(dto: PriceTypeDTO): PriceTypeDTO

    fun findAllByOrderByNAsc(): List<PriceTypeDTO>
    fun getByN(n: Long): PriceTypeDTO
}