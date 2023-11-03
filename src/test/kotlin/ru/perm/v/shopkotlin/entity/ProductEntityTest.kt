package ru.perm.v.shopkotlin.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ProductEntityTest {
    @Test
    internal fun create() {
        val GROUP = GroupProductEntity(10, "-")
        val p = ProductEntity(100, "NAME", GROUP.n)
        assertEquals(100, p.n)
        assertEquals("NAME", p.name)
        assertEquals(GROUP.n, p.groupProductN)
    }
}