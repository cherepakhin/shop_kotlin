package ru.perm.v.shopkotlin.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class ProductDTOTest {
    @Test
    fun create() {
        val N = 100L
        val NAME = "NAME"
        val GROUP_ID = -1L
        val p = ProductDTO(N, NAME, GROUP_ID)

        assertEquals(N, p.n)
        assertEquals(NAME, p.name)
        assertEquals(GROUP_ID, p.groupDtoN)
    }

    @Test
    fun equals() {
        val N = 100L
        val GROUP_N = -1L
        val product1 = ProductDTO(N, "NAME1", GROUP_N)
        val product2 = ProductDTO(N, "NAME1", GROUP_N)

        assertEquals(product1, product2) // as in java
    }

    @Test
    fun notEquals() {
        val product1 = ProductDTO(100L, "NAME1", -1L)
        val product2 = ProductDTO(100L, "NAME2", -1L)

        assertNotEquals(product1, product2) // as in java
    }
}