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
}