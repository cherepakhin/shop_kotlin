package ru.perm.v.shopkotlin.dto

import javax.validation.constraints.NotBlank

/**
 * DTO for GroupProduct
 *
 * @param n - N of GroupProduct
 * @param name - name of GroupProduct
 * @param parentN - parent GroupProduct N
 * @param haveChilds - have childs
 */
data class GroupProductDTO(
    val n: Long = -1,
    @field:NotBlank(message = "Name is empty")
    var name: String = "",
    val parentN: Long = -1,
    val haveChilds: Boolean = false
)