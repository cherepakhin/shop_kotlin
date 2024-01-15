package ru.perm.v.shopkotlin.service

import ru.perm.v.shopkotlin.dto.PriceTypeDTO

interface PriceTypeService {
    fun findAllByOrderByNAsc(): List<PriceTypeDTO>
}