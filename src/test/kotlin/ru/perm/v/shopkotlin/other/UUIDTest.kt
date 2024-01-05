package ru.perm.v.shopkotlin.other

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertFalse

internal class UUIDTest {
    @Test
    internal fun create() {
        val uuid = UUID.randomUUID().toString()

        assertFalse(uuid.isBlank())
    }
}