package ru.perm.v.shopkotlin.other;

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ListOf_Muttable_Unmuttable_Test {
    @Test
    fun muttableListTest() {
        val list = mutableListOf<Int>()
        list.add(200)

        assertEquals(1, list.size)
        assertEquals(200, list.get(0))
    }

    @Test
    internal fun unmuttableListTest() {
        val list = listOf<Int>(100)
//        list.add(200) // -> ERR, unmuttable

        assertEquals(1, list.size)
        assertEquals(100, list.get(0))
        assertEquals(100, list[0])
    }
}
