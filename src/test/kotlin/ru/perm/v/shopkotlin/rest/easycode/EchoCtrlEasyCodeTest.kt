package ru.perm.v.shopkotlin.rest.easycode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
class EchoCtrlEasyCodeTest(@Autowired private val mockMvc: MockMvc) {

    @Test
    fun `test echoStr endpoint`() {
        val expectedResponse = "hello"

        val result = mockMvc.perform(get("/echo/hello"))
            .andExpect(status().isOk)
            .andExpect(content().string(expectedResponse))
            .andReturn()

        val actualResponse = result.response.contentAsString
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `test echoStr endpoint with long message`() {
        val expectedResponse = "a".repeat(100000)

        val result = mockMvc.perform(get("/echo/$expectedResponse"))
            .andExpect(status().isOk)
            .andExpect(content().string(expectedResponse))
            .andReturn()

        val actualResponse = result.response.contentAsString
        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun test_from_inline() {
        val message = "MESSAGE"
        val result = mockMvc.perform(get("/echo/$message")).andExpect(status().isOk())
        assertEquals(message, result.andReturn().response.contentAsString)
    }
}
