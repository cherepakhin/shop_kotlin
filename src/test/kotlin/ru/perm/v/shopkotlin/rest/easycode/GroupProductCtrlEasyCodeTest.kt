package ru.perm.v.shopkotlin.rest.easycode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import ru.perm.v.shopkotlin.consts.ErrMessages
import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.rest.GroupProductRest
import ru.perm.v.shopkotlin.service.GroupProductService
import ru.perm.v.shopkotlin.service.ProductService
import javax.validation.ConstraintViolation
import javax.validation.Validator

// Сгенерировано плагином EasyCode в VSCode (9.01.2024)
// prompt: write unit test for GroupProductCtrl
class GroupProductCtrlEasyCodeTest {

    @Mock
    private lateinit var groupProductService: GroupProductService

    @Mock
    private lateinit var productService: ProductService

    @Mock
    private lateinit var validator: Validator

    private lateinit var groupProductRest: GroupProductRest

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        groupProductRest = GroupProductRest(groupProductService, productService)
    }

    @Test
    fun testEchoMessage() {
        val message = "Hello"
        val result = groupProductRest.echoMessage(message)
        assertEquals(message, result)
    }

    @Test
    fun testCreateValidGroupProduct() {
        val groupProductDTO = GroupProductDTO(1L, "Group", 0L, false)

        `when`(validator.validate(groupProductDTO)).thenReturn(emptySet())
        `when`(validator.validate(groupProductDTO)).thenReturn(emptySet())

        `when`(groupProductService.create(groupProductDTO)).thenReturn(groupProductDTO)
        val result = groupProductRest.create(groupProductDTO)

        assertEquals(groupProductDTO, result)
    }

    @Test
    fun testCreateInvalidGroupProduct() {
        val groupProductDTO = GroupProductDTO(1L, "", 0L, false)
        val violation: ConstraintViolation<GroupProductDTO> = mock()
        `when`(validator.validate(groupProductDTO)).thenReturn(setOf(violation))
        `when`(violation.message).thenReturn("Invalid name")

        val exception = assertThrows(Exception::class.java) {
            groupProductRest.create(groupProductDTO)
        }

        assertEquals(
            "GroupProductDTO(n=1, name=, parentN=0, haveChilds=false) has errors: Name is empty\n",
            exception.message
        )
    }

    @Test
    fun testGetByIdWithPositiveId() {
        val groupId = 1L
        val groupProductDTO = GroupProductDTO(groupId, "Group", 0L, false)
        `when`(groupProductService.getByN(groupId)).thenReturn(groupProductDTO)

        val result = groupProductRest.getById(groupId)

        assertEquals(groupProductDTO, result)
    }

    @Test
    fun testGetByIdWithNegativeId() {
        val groupId = -1L

        val exception = assertThrows(Exception::class.java) {
            groupProductRest.getById(groupId)
        }

        assertEquals(ErrMessages.ID_MUST_POSITIVE, exception.message)
    }

    // Add more tests for other methods...

}