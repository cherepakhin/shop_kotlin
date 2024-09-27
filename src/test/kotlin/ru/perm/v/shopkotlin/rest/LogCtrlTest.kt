package ru.perm.v.shopkotlin.rest

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class LogCtrlTest {

    @Test
    fun echoStr() {
        val ctrl = LogCtrl()

        assertTrue { ctrl.getLog().length > 0 }
    }
}