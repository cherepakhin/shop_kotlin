package ru.perm.v.shopkotlin.other

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MapOf_Test {

    @Test
    internal fun mapTo() {
        val mapSimple = mapOf(10 to "100", 20 to "200")

        assertEquals(2, mapSimple.size)
        assertEquals("100", mapSimple.get(10))
        assertEquals("100", mapSimple[10])
    }

    @Test
    internal fun mapFromPairs() {
        val pair1 = Pair(10, "100")
        val pair2 = Pair(20, "200")
        val mapPair = mapOf(pair1, pair2)

        assertEquals(2, mapPair.size)
        assertEquals("100", mapPair.get(10))
    }

    @Test
    internal fun unmuttableMapTest() {
        var myMap = mapOf(10 to 100, 20 to 200)

        assertEquals(2, myMap.size)
        assertEquals(100, myMap.get(10))
        assertEquals(200, myMap.get(20))

        myMap = myMap.plus(20 to 300)
        myMap = myMap.plus(40 to 400)

        assertEquals(3, myMap.size)
        assertEquals(100, myMap.get(10))
        assertEquals(300, myMap.get(20))
        assertEquals(400, myMap.get(40))

        assertEquals(100, myMap[10])
        assertEquals(300, myMap[20])
    }

    @Test
    internal fun unmuttableMapPlusTest() {
        var myMap = mapOf(10 to 100, 20 to 200)
        myMap = myMap.plus(20 to 300) // change on index 20
        myMap = myMap.plus(40 to 400)

        assertEquals(3, myMap.size)
        assertEquals(100, myMap.get(10))
        assertEquals(300, myMap.get(20))
        assertEquals(400, myMap.get(40))
    }

    @Test
    internal fun unmattableMapAccessAsArr() {
        val myMap = mapOf(10 to 100, 20 to 200)

        assertEquals(100, myMap[10])
        assertEquals(200, myMap[20])
    }

    @Test
    internal fun mapForObj() {
        data class User(val n: Long = 0L, var name: String = "");
        val userMap = mapOf(10 to User(10), 20 to User(20))
//        userMap[10] = 1000 //ERROR! "mapOf()" is IMMUTABLE,
//        for MUTTABLE use "mutableMapOf()". See test muttableMapAccessAsArr().

        assertEquals(2, userMap.size)
        assertEquals(User(10, ""), userMap.get(10))
    }

    @Test
    internal fun muttableMapAccessAsArr() {
        val muttableMyMap = mutableMapOf(10 to 100, 20 to 200)
        muttableMyMap[10] = 1000
        muttableMyMap[20] = 2000

        assertEquals(1000, muttableMyMap[10])
        assertEquals(2000, muttableMyMap[20])
    }

}