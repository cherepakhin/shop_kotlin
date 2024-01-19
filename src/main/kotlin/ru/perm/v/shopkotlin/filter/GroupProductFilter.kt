package ru.perm.v.shopkotlin.filter

/**
 * Фильтр для групп с указанием сортировки выборки
 */
data class GroupProductFilter(
    val listN: List<Long>,
    val name: String = "",
    var listSortBy: List<String> = listOf("name")
)
