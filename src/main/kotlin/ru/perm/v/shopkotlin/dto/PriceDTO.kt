package ru.perm.v.shopkotlin.dto

import java.math.BigDecimal

/**
 * DTO for Price
 *
 * @param productDTO - ProductDTO
 * @param priceTypeDTO - PriceTypeDTO
 * @param price - price of Product
 */
data class PriceDTO(
    val n: Long = -1,
    var productDTO: ProductDTO = ProductDTO(),
    var priceTypeDTO: PriceTypeDTO = PriceTypeDTO(),
    var price: BigDecimal = BigDecimal.ZERO
)
