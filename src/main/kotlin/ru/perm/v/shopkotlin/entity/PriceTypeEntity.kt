package ru.perm.v.shopkotlin.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Entity for PriceType
 * n - N of PriceType
 * name - name of PriceType
 */
@Entity
@Table(name = "pricetype")
data class PriceTypeEntity(
    @Id
    val n:Long = -1,
    val name:String = "-"
)