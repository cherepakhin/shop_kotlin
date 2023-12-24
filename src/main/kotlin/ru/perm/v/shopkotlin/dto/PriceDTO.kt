package ru.perm.v.shopkotlin.dto

import java.math.BigDecimal

data class PriceDTO(
    var productDTO: ProductDTO = ProductDTO(),
    var price: BigDecimal = BigDecimal.ZERO
)
