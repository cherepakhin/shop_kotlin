package ru.perm.v.shopkotlin.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class ProductEntityTest {
    @Test
    fun create() {
        val GROUP_PRODUCT_N = 10L

        val p = ProductEntity(100, "NAME", GROUP_PRODUCT_N)

        assertEquals(100, p.n)
        assertEquals("NAME", p.name)
        assertEquals(GROUP_PRODUCT_N, p.groupProductN)
    }

    @Test
    fun equals() {
        val product1 = ProductEntity(1L, "name", 100L)
        val product2 = ProductEntity(1L, "name", 100L)

        assertEquals(product1, product2)
        // Равенство структур == - проверка через equals()
        // Равенство ссылок === - две ссылки указывают на один и тот же объект
        assert(product1.hashCode() == product2.hashCode())
    }

    @Test
    fun notEqualsByN() {
        val product1 = ProductEntity(1L, "name", 100L)
        val product2 = ProductEntity(2L, "name", 100L)

        assertNotEquals(product1, product2)
    }

    @Test
    fun notEqualsByName() {
        val product1 = ProductEntity(1L, "name", 100L)
        val product2 = ProductEntity(1L, "name1", 100L)

        assertNotEquals(product1, product2)
    }

    @Test
    fun notEqualsByGroupProductN() {
        val product1 = ProductEntity(1L, "name", 0L)
        val product2 = ProductEntity(1L, "name", 1L)

        assertNotEquals(product1, product2)
    }

    @Test
    fun hashCodeTest() {
        val product1 = ProductEntity(1L, "name", 1L)
        val product2 = ProductEntity(1L, "name", 1L)

        assertEquals(product1.hashCode(), product2.hashCode())
        assert(product1.hashCode() == product2.hashCode())
    }
}