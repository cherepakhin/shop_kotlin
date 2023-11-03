package ru.perm.v.shopkotlin.filter

data class GroupProductFilter(
    val listN: List<Long>,
    val name: String = "",
    var listSortBy: List<String> = listOf("n")
)
