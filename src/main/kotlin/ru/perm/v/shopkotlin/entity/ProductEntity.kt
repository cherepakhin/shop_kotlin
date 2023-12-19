package ru.perm.v.shopkotlin.entity

import javax.persistence.*

@Entity
@Table(name = "product")
class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // hibernate sequence by next max
    @Column(name = "n", nullable = false)
    // column name must is not "id", "id" is key word
    var n: Long = -1L

    @Column(name = "name", nullable = false)
    var name: String = ""

    @Column(name = "group_product_n", nullable = false)
    var groupProductN: Long = -1
    // need constructor fro queryDsl. By default query Dsl use simple constructor ProductEntity()
    constructor(n: Long, name: String, groupProductN: Long) {
        this.n = n
        this.name = name
        this.groupProductN = groupProductN
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ProductEntity) return false

        if (n != other.n) return false
        if (name != other.name) return false
        if (groupProductN != other.groupProductN) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + groupProductN.hashCode()
        return result
    }

}
