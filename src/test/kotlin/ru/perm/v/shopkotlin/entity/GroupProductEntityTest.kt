package ru.perm.v.shopkotlin.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class GroupProductEntityTest {
    @Test
    fun constructor() {
        val group = GroupProductEntity(0, "NAME", -1, true)

        assertEquals(GroupProductEntity(0, "NAME", -1, true), group)
    }

    @Test
    fun constructorWithDefaultIsLast() {
        val group = GroupProductEntity(0, "NAME", -1, false)

        assertEquals(GroupProductEntity(0, "NAME", -1, false), group)
    }

    @Test
    fun checkEqualHash() {
        val group1 = GroupProductEntity(0, "NAME", -1, false)
        assertEquals(GroupProductEntity(0, "NAME", -1, false).hashCode(), group1.hashCode())
    }

    @Test
    fun equals() {
        val group1 = GroupProductEntity(0, "NAME", -1, false)
        val group2 = GroupProductEntity(0, "NAME", -1, false)

        assertEquals(group1, group2)
    }

    @Test
    fun notEqualsName() {
        val group1 = GroupProductEntity(0, "NAME1", -1, true)
        val group2 = GroupProductEntity(0, "NAME", -1, true)

        assertNotEquals(group1, group2)
    }

    @Test
    fun notEqualsN() {
        val group1 = GroupProductEntity(0, "NAME", -1, false)
        val group2 = GroupProductEntity(1, "NAME", -1, false)

        assertNotEquals(group1, group2)
    }

    @Test
    fun notEqualsParentN() {
        val group1 = GroupProductEntity(0, "NAME", -1, false)
        val group2 = GroupProductEntity(0, "NAME", 0, false)

        assertNotEquals(group1, group2)
    }

    @Test
    fun notEqualsHaveChilds() {
        val group1 = GroupProductEntity(0, "NAME1", -1, false)
        val group2 = GroupProductEntity(0, "NAME1", -1, true)

        assertNotEquals(group1, group2)
    }
}