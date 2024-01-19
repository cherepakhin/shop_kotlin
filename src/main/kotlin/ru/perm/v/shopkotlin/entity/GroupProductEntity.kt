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
class GroupProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n")
    // COLUMN NAME MUST IS NOT "ID", "ID" IS KEY WORD IN h2database
    val n: Long = -1,
    @Column(name = "name", nullable = false)
    val name: String = "",
    @Column(name = "parent_n", nullable = false)
    val parentN: Long = -1,
    @Column(name = "have_childs", nullable = false)
    val haveChilds: Boolean = false
) {
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
}