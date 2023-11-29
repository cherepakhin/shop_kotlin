package ru.perm.v.shopkotlin.other

import io.swagger.v3.oas.annotations.media.Schema

class NestedClassExample(
    var errors: List<Error> = listOf()
) {

    data class Error(

        @Schema(description = "Код ошибки", maxLength = 255)
        val code: String,

        @Schema(description = "Ключ/название ошибочного поля", maxLength = 255)
        var name: String,
        )

}