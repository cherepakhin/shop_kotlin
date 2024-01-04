package ru.perm.v.shopkotlin.dto.easycode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.rest.GroupProductRest
import ru.perm.v.shopkotlin.service.ProductService
import ru.perm.v.shopkotlin.service.impl.GroupProductServiceImpl
import ru.perm.v.shopkotlin.service.impl.ProductServiceImpl

class GroupProductDTOEasyCodeTest {
    @Test
    fun testDefaultValues() {
        // Arrange
        val groupProductDTO = GroupProductDTO()

        // Assert
        assertEquals(-1, groupProductDTO.n)
        assertEquals("", groupProductDTO.name)
        assertEquals(-1, groupProductDTO.parentN)
        assertEquals(false, groupProductDTO.haveChilds)
    }

    @Test
    fun testConstructor() {
        // Arrange
        val n = 1L
        val name = "Test Group"
        val parentN = 2L
        val haveChilds = true
        val groupProductDTO = GroupProductDTO(n, name, parentN, haveChilds)

        // Assert
        assertEquals(n, groupProductDTO.n)
        assertEquals(name, groupProductDTO.name)
        assertEquals(parentN, groupProductDTO.parentN)
        assertEquals(haveChilds, groupProductDTO.haveChilds)
    }

    @Test
    fun testValidation() {
        // Arrange
        val groupProductService = Mockito.mock(GroupProductServiceImpl::class.java)
        val productService: ProductService = Mockito.mock(ProductServiceImpl::class.java)

        val groupProductDTO = GroupProductDTO()
        groupProductDTO.name = ""

        val rest = GroupProductRest(groupProductService, productService)
        val violations = rest.validator.validate(groupProductDTO)
        assertEquals(1, violations.size)

        val violation= violations.elementAt(0)
        assertEquals("Name is empty", violation.message)
    }
}
