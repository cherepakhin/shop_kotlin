package ru.perm.v.shopkotlin.service

import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.filter.ProductFilter

interface ProductService {
    @Throws(Exception::class)
    fun create(dto: ProductDTO): ProductDTO

    @Throws(Exception::class)
    fun update(dto: ProductDTO): ProductDTO

    @Throws(Exception::class)
    fun delete(n: Long)
    fun existByN(n: Long): Boolean
    fun getByGroupProductN(n: Long): List<ProductDTO>

    @Throws(Exception::class)
    fun getByN(n: Long): ProductDTO
    fun getByNs(nn: List<Long>): List<ProductDTO>
    fun getByFilter(filter: ProductFilter): List<ProductDTO>
    fun getByName(name: String): List<ProductDTO>
    fun getCountOfProductNames(): Long
    fun getAll(): List<ProductDTO>
}