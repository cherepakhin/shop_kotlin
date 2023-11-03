package ru.perm.v.shopkotlin.bito.rest

import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.perm.v.shopkotlin.rest.EchoCtrl
import kotlin.test.assertEquals

class EchoCtrlTest {
    private val echoCtrl = EchoCtrl()

    @Test
    fun `echoStr should return the same message`() {
//        val message = "Hello World"
//        val result = echoCtrl.echoStr(message)
//        assertEquals(message, result)
        val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(echoCtrl).build()
        val result = mockMvc.perform(get("/echo/MESSAGE"))
            .andExpect(status().isOk)
            .andReturn()
        assertEquals("MESSAGE", result.response.contentAsString)
    }

    @Test
    fun `echoStr should return 404 for empty message`() {
        val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(echoCtrl).build()
        mockMvc.perform(get("/"))
            .andExpect(status().isNotFound)
    }
}