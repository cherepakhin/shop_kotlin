package ru.perm.v.shopkotlin.rest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.WebApplicationContext
import ru.perm.v.shopkotlin.consts.ErrMessages
import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.service.GroupProductService
import ru.perm.v.shopkotlin.service.ProductService
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.ValidatorFactory

@ExtendWith(MockitoExtension::class)
class GroupProductRestTest {
    @Mock
    private lateinit var mockGroupProductService: GroupProductService

    @Mock
    private lateinit var mockProductService: ProductService

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Test
    fun `test all method when groups found`() {
        val controller = GroupProductRest(mockGroupProductService, mockProductService)
        // Given
        val group1 = GroupProductDTO(1, "Group 1", 0, true)
        val group2 = GroupProductDTO(2, "Group 2", 1, true)
        val groups = listOf(group1, group2)
        `when`(mockGroupProductService.findAllByOrderByNAsc()).thenReturn(groups)
        // When
        val result = controller.all()
        // Then
        assertEquals(2, result.size)
        assertEquals("Group 1", result[0].name)
        assertEquals("Group 2", result[1].name)
    }

    @Test
    fun `test all method when no groups found`() {
        val controller = GroupProductRest(mockGroupProductService, mockProductService)
        // Given
        `when`(mockGroupProductService.findAllByOrderByNAsc()).thenReturn(emptyList())
        // When
        val exception = assertThrows<Exception> { controller.all() }
        // Then
        assertEquals(ErrMessages.NOT_FOUND_ANY_GROUP, exception.message)
    }

    @Test
    fun deleteByN() {
        val controller = GroupProductRest(mockGroupProductService, mockProductService)
        val N = 100L

        `when`(mockGroupProductService.existsByN(N)).thenReturn(true)
        doNothing().`when`(mockGroupProductService).deleteByN(N)

        controller.deleteByN(N)

        Mockito.verify(mockGroupProductService, Mockito.times(1)).deleteByN(N)
    }

    @Test
    fun deleteByIdForNotExist() {
        val controller = GroupProductRest(mockGroupProductService, mockProductService)
        val N = 100L

        `when`(mockGroupProductService.existsByN(N)).thenReturn(false)

        assertThrows<Exception> { controller.deleteByN(N) }
        Mockito.verify(mockGroupProductService, Mockito.times(0)).deleteByN(N)
    }

    @Test
    fun checkExceptionOnDeleteByIdForExistSubgroup() {
        val controller = GroupProductRest(mockGroupProductService, mockProductService)
        val N = 100L

        `when`(mockGroupProductService.existsByN(N)).thenReturn(true)
        `when`(mockGroupProductService.getSubGroups(N)).thenReturn(listOf(GroupProductDTO()))

        assertThrows<Exception> { controller.deleteByN(N) }
        Mockito.verify(mockGroupProductService, Mockito.times(0)).deleteByN(N)
    }

    @Test
    fun findByName() {
        val PARENT_GROUP_N = 1L
        val GROUP_N = 100L
        val GROUP_NAME = "GROUP_NAME"
        val groups = listOf(GroupProductDTO(GROUP_N, GROUP_NAME, PARENT_GROUP_N, true))
        `when`(mockGroupProductService.findByNameContaining(GROUP_NAME)).thenReturn(groups)
        val controller = GroupProductRest(mockGroupProductService, mockProductService)
        val dtos = controller.findByName(GROUP_NAME)

        assertEquals(1, dtos.size)
    }

    @Test
    fun create() {
        val groupDTO = GroupProductDTO(1, "Group 1", 0, true)
        val groupDTOForMock = GroupProductDTO(1, "Created Group 1", 0, true)
        `when`(mockGroupProductService.create(groupDTO)).thenReturn(groupDTOForMock)
        val controller = GroupProductRest(mockGroupProductService, mockProductService)

        val createdDTO = controller.create(groupDTO)

        assertEquals(GroupProductDTO(1, "Created Group 1", 0, true), createdDTO)
        Mockito.verify(mockGroupProductService, Mockito.times(1)).create(groupDTO)
    }

    @Test
    fun validateGroupProductDTOWithEmptyName() {
        val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
        val validator: javax.validation.Validator = factory.validator
        val violations: MutableSet<ConstraintViolation<GroupProductDTO>> =
            validator.validate(GroupProductDTO(1, "", 0, true))

        assertEquals(1, violations.size)
        assertEquals("Name is empty", violations.elementAt(0).message)
    }


    @Test
    fun createWithNotValidDTO() {
        val groupDTO = GroupProductDTO(1, "", -100, true)
        val controller = GroupProductRest(mockGroupProductService, mockProductService)

        val excpt = assertThrows<Exception> {
            controller.create(groupDTO)
        }

        assertEquals(
            "GroupProductDTO(n=1, name=, parentN=-100, haveChilds=true) has errors: Name is empty\n",
            excpt.message
        )
        Mockito.verify(mockGroupProductService, Mockito.times(0)).create(groupDTO)
    }

    @Test
    fun findByNotFoundName() {
        val GROUP_NAME = "GROUP_NAME"

        `when`(mockGroupProductService.findByNameContaining(GROUP_NAME)).thenReturn(emptyList())
        val controller = GroupProductRest(mockGroupProductService, mockProductService)
        val excpt = assertThrows<Exception> {
            controller.findByName(GROUP_NAME)
        }

        assertEquals(
            "Group product not found with name=GROUP_NAME",
            excpt.message
        )
        Mockito.verify(mockGroupProductService, Mockito.times(1))
            .findByNameContaining(GROUP_NAME)
    }

    @Test
    fun deleteByN_IfExistProductsInGroup() {
        val controller = GroupProductRest(mockGroupProductService, mockProductService)
        val GROUP_N = 100L

        `when`(mockGroupProductService.existsByN(GROUP_N)).thenReturn(true)
        `when`(mockProductService.getByGroupProductN(GROUP_N)).thenReturn(listOf(ProductDTO()))

        val excpt = assertThrows<Exception> {
            controller.deleteByN(GROUP_N)
        }

        assertEquals(
            "Group product with id: 100 contains products. Remove them to other group first.",
            excpt.message
        )
        Mockito.verify(mockGroupProductService, Mockito.times(0)).deleteByN(GROUP_N)
    }

    @Test
    fun getSubGroups() {
        val controller = GroupProductRest(mockGroupProductService, mockProductService)
        val GROUP_N = 100L
        val SUB_GROUP_N_101 = 101L
        val SUB_GROUP_N_102 = 102L

        `when`(mockGroupProductService.existsByN(GROUP_N)).thenReturn(true)
        `when`(mockGroupProductService.getSubGroups(GROUP_N))
            .thenReturn(
                listOf(
                    GroupProductDTO(SUB_GROUP_N_101),
                    GroupProductDTO(SUB_GROUP_N_102),
                )
            )

        val subGroups = controller.getSubGroups(GROUP_N)

        assertEquals(2, subGroups.size)
        assertEquals(SUB_GROUP_N_101, subGroups.get(0).n)
        assertEquals(SUB_GROUP_N_102, subGroups.get(1).n)
    }

    @Test
    fun getSubGroupsForNotExistMainGroup() {
        val controller = GroupProductRest(mockGroupProductService, mockProductService)
        val GROUP_N = 100L

        `when`(mockGroupProductService.existsByN(GROUP_N)).thenReturn(false)
        val excpt = assertThrows<Exception> {
            controller.getSubGroups(GROUP_N)
        }

        assertEquals(
            "Group product not found with id: 100",
            excpt.message
        )
        Mockito.verify(mockGroupProductService, Mockito.times(0)).getSubGroups(GROUP_N)
    }
}