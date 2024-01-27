package ru.perm.v.shopkotlin.entity

import javax.persistence.*

/**
 * Entity for GroupProduct
 *
 * n - N of GroupProduct
 * name - name of GroupProduct
 * parentN - parent GroupProduct N
 * haveChilds - have childs of GroupProduct
 */
@Entity
@Table(name = "group_product")
open class GroupProductEntity { // open needed for Hibernate
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n")
    // COLUMN NAME MUST IS NOT "ID", "ID" IS KEY WORD IN h2database
    var n: Long = -1
    @Column(name = "name", nullable = false)
    var name: String = ""
    @Column(name = "parent_n", nullable = false)
    var parentN: Long = -1
    @Column(name = "have_childs", nullable = false)
    var haveChilds: Boolean = false

    // Empty constructor needed for Hibernate
    constructor() {

    }

    constructor(n: Long, name: String) {
        this.n = n
        this.name = name
    }

    constructor(n: Long, name: String, parentN: Long, haveChilds: Boolean) {
        this.n = n
        this.name = name
        this.parentN = parentN
        this.haveChilds = haveChilds
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GroupProductEntity) return false

        if (n != other.n) return false
        if (name != other.name) return false
        if (parentN != other.parentN) return false
        if (haveChilds != other.haveChilds) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + parentN.hashCode()
        result = 31 * result + haveChilds.hashCode()
        return result
    }

    override fun toString(): String {
        return "GroupProductEntity(n=$n, name='$name', parentN=$parentN, haveChilds=$haveChilds)"
    }


}