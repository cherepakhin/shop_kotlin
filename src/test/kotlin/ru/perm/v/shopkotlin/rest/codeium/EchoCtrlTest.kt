package ru.perm.v.shopkotlin.rest.codeium;

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import ru.perm.v.shopkotlin.rest.EchoCtrl

@ExtendWith(SpringExtension::class)
@WebMvcTest(EchoCtrl::class)
class EchoCtrlTest {

    @Autowired
    lateinit private var mockMvc: MockMvc

    @Test
    fun testEchoStr() {
        val mes = mockMvc.perform(get("/echo/ECHO_MESSAGE")).andReturn().response.contentAsString

        assertEquals("ECHO_MESSAGE", mes)
    }

}
