package ru.perm.v.shopkotlin.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GroupProductEntityTest {
    @Test
    fun constructor() {
        val group = GroupProductEntity(0, "NAME", -1, true)
        assertEquals(GroupProductEntity(0, "NAME", -1, true), group)
    }

    @Test
    fun constructorWithDefaultIsLast() {
        val group = GroupProductEntity(0, "NAME", -1)
        assertEquals(GroupProductEntity(0, "NAME", -1, false), group)
    }

    @Test
    fun checkEqualHash() {
        val group1 = GroupProductEntity(0, "NAME", -1)
        assertEquals(GroupProductEntity(0, "NAME", -1, false).hashCode(), group1.hashCode())
    }
}