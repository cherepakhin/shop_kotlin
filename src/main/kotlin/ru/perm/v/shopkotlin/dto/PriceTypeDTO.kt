package ru.perm.v.shopkotlin.dto

/**
 * DTO for PriceType
 *
 * @param n - N of PriceType
 * @param name - name of PriceType
 */
data class PriceTypeDTO(
    val n:Long = -1,
    val name:String = "-"
)
