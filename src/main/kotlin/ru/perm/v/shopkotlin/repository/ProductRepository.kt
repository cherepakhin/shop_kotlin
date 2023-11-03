package ru.perm.v.shopkotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.perm.v.shopkotlin.entity.ProductEntity

@Repository
@Transactional
interface ProductRepository : JpaRepository<ProductEntity, Long>,
    JpaSpecificationExecutor<ProductEntity>, QuerydslPredicateExecutor<ProductEntity> {
//interface ProductRepository : ExCustomRepository<ProductEntity, QProductEntity, Long> {

    fun findAllByOrderByNAsc(): List<ProductEntity>
    fun findAllByGroupProductNOrderByNAsc(groupProductN: Long): List<ProductEntity>

    @Query(value = "select max(p.n)+1 from product p", nativeQuery = true)
    fun getNextN(): Long

    fun findByNameContainingOrderByName(name: String): List<ProductEntity>

    @Query(
        "select count(*) from ProductEntity"
    )
    fun getCountOfProductNames(): Long
}