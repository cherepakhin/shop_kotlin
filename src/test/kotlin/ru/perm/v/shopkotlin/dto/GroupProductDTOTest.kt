package ru.perm.v.shopkotlin.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class GroupProductDTOTest {

    @Test
    fun constructor() {
        val N = 100L
        val NAME = "NAME"
        val PARENT_N = 101L
        val group = GroupProductDTO(N, NAME, PARENT_N, true)
        assertEquals(N, group.n)
        assertEquals(NAME, group.name)
        assertTrue(group.haveChilds)
    }
}