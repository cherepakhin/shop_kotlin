package ru.perm.v.shopkotlin.filter

/**
 * Filter for GroupProduct with listN, name and listSortBy
 */
data class GroupProductFilter(
    val listN: List<Long>,
    val name: String = "",
    var listSortBy: List<String> = listOf("name")
)
