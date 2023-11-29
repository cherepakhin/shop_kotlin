package ru.perm.v.shopkotlin.other

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class NestedClassExampleTest {
    @Test
    fun empty() {
        val example = NestedClassExample()

        assertEquals(0, example.errors.size)
    }

    @Test
    fun existNested() {
        val example = NestedClassExample(listOf(NestedClassExample.Error("code1", "name1")))

        assertEquals(1, example.errors.size)
        assertEquals("name1", example.errors.get(0).name)
        assertEquals(NestedClassExample.Error("code1", "name1"), example.errors.get(0))
    }
}