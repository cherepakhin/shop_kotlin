package ru.perm.v.shopkotlin.other

import org.junit.jupiter.api.Test
import ru.perm.v.shopkotlin.extdto.AddressExtDTO
import kotlin.test.assertEquals

/**
 * Test access to library from external nexus repository url = uri("http://192.168.1.20:8082/repository/ru.perm.v")
 * implementation("ru.perm.v:shop_kotlin_extdto:0.0.2@jar")
 * implementation("ru.perm.v:shop_kotlin_extdto:0.0.3")
 */
class FromExternalRepositoryAddressDtoTest {
    @Test
    internal fun isAccessToExternalDto() {
        val addr1 = AddressExtDTO(1L, "TOWN_1", "STREET_1", "HOUSE_1")

        assertEquals(1L, addr1.n)
        assertEquals("TOWN_1", addr1.town)
        assertEquals("STREET_1", addr1.street)
        assertEquals("HOUSE_1", addr1.house)
    }
}