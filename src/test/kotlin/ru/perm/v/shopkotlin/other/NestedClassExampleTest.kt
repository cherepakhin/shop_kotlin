package ru.perm.v.shopkotlin.other

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Использование внутренних классов.
 */
class NestedClassExampleTest {
    @Test
    fun empty() {
        val example = NestedClassExample()

        assertEquals(0, example.errors.size)
    }

    @Test
    fun existWithCreate() {
        val example = NestedClassExample()

        val err = NestedClassExample.Error("code1", "name1")
        err.name = "name"
        example.errors = listOf(err)
        assertEquals(1, example.errors.size)
        assertEquals("name", example.errors.get(0).name)
    }

    @Test
    fun existNested() {
        val example = NestedClassExample(listOf(NestedClassExample.Error("code1", "name1")))

        assertEquals(1, example.errors.size)
        assertEquals("name1", example.errors.get(0).name)
        assertEquals(NestedClassExample.Error("code1", "name1"), example.errors.get(0))
    }
}