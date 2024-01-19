package ru.perm.v.shopkotlin.filter

/**
 * Filter for GroupProduct with listN, name and listSortBy
 */
data class GroupProductFilter(
    val listN: List<Long>, // list of GroupProduct N
    val name: String = "", // name of GroupProduct
    var listSortBy: List<String> = listOf("name") // list of sort fields
)
