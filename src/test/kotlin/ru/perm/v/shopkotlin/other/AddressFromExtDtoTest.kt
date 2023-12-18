package ru.perm.v.shopkotlin.other

import org.junit.jupiter.api.Test
import ru.perm.v.shopkotlin.extdto.AddressExtDto
import kotlin.test.assertEquals

/**
 * Test access to external nexus repository url = uri("http://192.168.1.20:8082/repository/ru.perm.v")
 */
class AddressFromExtDtoTest {
    @Test
    internal fun testAccess() {
        val addr1 = AddressExtDto(1L, "NAME");
        assertEquals(1L, addr1.n)
        assertEquals("NAME", addr1.name)
    }
}