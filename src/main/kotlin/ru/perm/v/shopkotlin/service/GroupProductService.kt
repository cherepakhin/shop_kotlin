package ru.perm.v.shopkotlin.service

import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.filter.GroupProductFilter

interface GroupProductService {
    fun create(groupProductDTO: GroupProductDTO): GroupProductDTO
    fun update(groupProductDTO: GroupProductDTO): GroupProductDTO
    fun deleteByN(n: Long)
    fun getSubGroups(n: Long): List<GroupProductDTO>
    fun findAllByOrderByNAsc(): List<GroupProductDTO>
    fun findByNameContaining(name: String): List<GroupProductDTO>
    fun existProductsInGroup(n: Long): Boolean

    @Throws(Exception::class)
    fun getByN(n: Long): GroupProductDTO
    fun existsByN(n: Long): Boolean
    fun getByFilter(filter: GroupProductFilter): List<GroupProductDTO>
}