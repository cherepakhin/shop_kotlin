package ru.perm.v.shopkotlin.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Entity for PriceType
 * n - N of PriceType
 * name - name of PriceType
 */

// NOT DATA CLASS!
// data class PriceTypeEntity(var n: Long = -1, var name: String = "-")
// Warning:(14, 12) The data class implementations of equals(), hashCode() and toString()
// are not recommended !!!ONLY FOR JPA ENTITIES!!!. They can cause severe performance and memory consumption issues.
@Entity
@Table(name = "pricetype")
open class PriceTypeEntity { // open - can be inherited and needed for Hibernate
    @Id
    @Column(name = "n", nullable = false)
    var n: Long = -1
    @Column(name = "name", nullable = false)
    var name: String = ""

    // Empty constructor needed for Hibernate
    constructor()

    constructor(n: Long, name: String) {
        this.n = n
        this.name = name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PriceTypeEntity) return false

        if (n != other.n) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "PriceTypeEntity(n=$n, name='$name')"
    }


}
