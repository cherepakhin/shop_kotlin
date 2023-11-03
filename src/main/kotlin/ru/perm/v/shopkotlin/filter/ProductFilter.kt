package ru.perm.v.shopkotlin.filter

data class ProductFilter(
    val listN: List<Long>,
    val name: String = "",
    var listSortBy: List<String> = listOf("name")
)
