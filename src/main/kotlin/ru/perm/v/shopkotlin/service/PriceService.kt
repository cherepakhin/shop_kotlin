package ru.perm.v.shopkotlin.service

import ru.perm.v.shopkotlin.dto.PriceDTO

interface PriceService {

    @Throws(Exception::class)
    fun create(dto: PriceDTO): PriceDTO

    fun findAllByOrderByNAsc(): List<PriceDTO>
    fun getByN(n: Long): PriceDTO
}