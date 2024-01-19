package ru.perm.v.shopkotlin.dto

import ru.perm.v.shopkotlin.consts.ErrMessages
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * DTO for Product
 *
 * @param n - N of Product
 * @param name - name of Product
 * @param groupDtoN - N of Group
 */
class ProductDTO {
    @field:NotNull
    var n: Long = -1L

    @field:NotEmpty(message = ErrMessages.FIELD_PRODUCT_NAME_EMPTY)
    var name: String = "-"

    @field:NotNull
    var groupDtoN: Long = -1

    // need for Mockito
    constructor()

    // I don't create a constructor from ProductEntity, because I don't want import ProductEntity
    constructor(n: Long, name: String, groupDtoN: Long) {
        this.n = n
        this.name = name
        this.groupDtoN = groupDtoN
    }

    // equals and hash as in java
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductDTO

        if (n != other.n) return false
        if (name != other.name) return false
        if (groupDtoN != other.groupDtoN) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + groupDtoN.hashCode()
        return result
    }

    override fun toString(): String {
        return "ProductDTO(n=$n, name='$name', groupDtoN=$groupDtoN)"
    }
}