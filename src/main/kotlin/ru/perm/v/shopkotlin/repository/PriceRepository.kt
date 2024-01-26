package ru.perm.v.shopkotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.perm.v.shopkotlin.entity.PriceEntity

@Repository
@Transactional
interface PriceRepository : JpaRepository<PriceEntity, Long>,
    JpaSpecificationExecutor<PriceEntity>, QuerydslPredicateExecutor<PriceEntity> {

    fun findAllByOrderByNAsc(): List<PriceEntity>
}