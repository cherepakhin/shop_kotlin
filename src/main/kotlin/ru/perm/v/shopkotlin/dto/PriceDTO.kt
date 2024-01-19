package ru.perm.v.shopkotlin.dto

import java.math.BigDecimal

/**
 * DTO for Price
 *
 * @param productDTO - ProductDTO
 * @param price - price of Product
 */
data class PriceDTO(
    var productDTO: ProductDTO = ProductDTO(),
    var price: BigDecimal = BigDecimal.ZERO
)
