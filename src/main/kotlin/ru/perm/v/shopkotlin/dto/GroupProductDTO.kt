package ru.perm.v.shopkotlin.dto

import javax.validation.constraints.NotBlank

data class GroupProductDTO(
    val n: Long = -1,
    @field:NotBlank(message = "Name is empty")
    val name: String = "",
    val parentN: Long = -1,
    val haveChilds: Boolean = false
)