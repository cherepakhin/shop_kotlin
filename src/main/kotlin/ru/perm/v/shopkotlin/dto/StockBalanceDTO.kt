package ru.perm.v.shopkotlin.dto

data class StockBalanceDTO(
    val stockDTO: StockDTO,
    val productDTO: ProductDTO = ProductDTO(),
    val qty: Long = 0
)