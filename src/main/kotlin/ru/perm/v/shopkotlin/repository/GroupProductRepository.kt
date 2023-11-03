package ru.perm.v.shopkotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.perm.v.shopkotlin.entity.GroupProductEntity

@Repository
@Transactional
interface GroupProductRepository : JpaRepository<GroupProductEntity, Long>,
    JpaSpecificationExecutor<GroupProductEntity>, QuerydslPredicateExecutor<GroupProductEntity> {
    fun findAllByOrderByNAsc(): List<GroupProductEntity>
    fun findByNameContaining(name: String): List<GroupProductEntity>
    fun findAllByParentN(n: Long): List<GroupProductEntity>

    @Query(value = "select max(g.n)+1 from group_product g", nativeQuery = true)
    fun getNextN(): Long
    fun getByN(n: Long): GroupProductEntity
    fun deleteByN(n: Long)
    fun existsByN(n: Long): Boolean
}