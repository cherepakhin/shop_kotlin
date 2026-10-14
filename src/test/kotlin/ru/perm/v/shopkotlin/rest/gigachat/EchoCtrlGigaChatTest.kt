package ru.perm.v.shopkotlin.rest.gigachat

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.perm.v.shopkotlin.rest.EchoCtrl

class EchoCtrlGigaChatTest {
    @Test
    fun echo() {
        val echoCtrl = EchoCtrl()
        val result = echoCtrl.echoStr("Hello")

        assertEquals("Hello", result)
    }
}