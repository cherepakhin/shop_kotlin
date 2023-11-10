package ru.perm.v.shopkotlin.other

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Pair_mapOf_to_Test {
    @Test
    internal fun mapTo() {
        val mapSimple = mapOf(10 to "100", 20 to "200")
        assertEquals(2, mapSimple.size)
        assertEquals("100", mapSimple.get(10))
    }

    @Test
    internal fun pair() {
        val pair1 = Pair(10, "100")
        val pair2 = Pair(20, "200")
        val mapPair = mapOf(pair1, pair2)
        assertEquals(2, mapPair.size)
        assertEquals("100", mapPair.get(10))
    }

    @Test
    internal fun muttableListTest() {
        val list = mutableListOf<Int>()
        list.add(200)
        assertEquals(1, list.size)
        assertEquals(200, list.get(0))
    }

    @Test
    internal fun unmuttableListTest() {
        val list = listOf<Int>(100)
//        list.add(200) -> ERR, unmuttable
        assertEquals(1, list.size)
        assertEquals(100, list.get(0))
    }

    @Test
    internal fun unmattableMapTest() {
        var map = mapOf(10 to 100, 20 to 200)
        assertEquals(2, map.size)
        assertEquals(100, map.get(10))
        assertEquals(200, map.get(20))

        map = map.plus(20 to 300)
        map = map.plus(40 to 400)
        assertEquals(3, map.size)
        assertEquals(100, map.get(10))
        assertEquals(300, map.get(20))
        assertEquals(400, map.get(40))
    }
}