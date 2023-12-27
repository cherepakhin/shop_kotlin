package ru.perm.v.shopkotlin.service.impl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.filter.GroupProductFilter
import ru.perm.v.shopkotlin.repository.GroupProductRepository
import ru.perm.v.shopkotlin.service.ProductService
import kotlin.test.assertContentEquals

//TODO: create import.sql for test
@ExtendWith(SpringExtension::class)
@DataJpaTest
// "Normally, Spring Boot will start an IN-MEMORY(!!!) database for @DataJpaTest-Tests.
// We use AutoConfigureTestDatabase to explicitly deactivate this default behavior.
// This way, the tests will run against our PostgreSQL Database."
// BUT, my database is H2, therefore
//      @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

// My database is "in memory database!". Therefore, next string is commented
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GroupProductServiceIntegrationTest {
    @Autowired
    private lateinit var groupProductRepository: GroupProductRepository

    @Mock
    private lateinit var productService: ProductService

    @Test
    fun demoQueryDslCheckSize() {
        val groupProductService = GroupProductServiceImpl(groupProductRepository, productService)
        assertEquals(7, groupProductService.repository.findAllByOrderByNAsc().size)
    }

    @Test
    fun demoQueryDslCheckIndex0() {
        val groupProductService = GroupProductServiceImpl(groupProductRepository, productService)
        val groups = groupProductService.repository.findAllByOrderByNAsc()
        assertEquals(1, groups.get(0).n)
    }

// not work in jenkins. error:
//      file:///home/vasi/.jenkins/workspace/shop_kotlin_pipe/src/test/
//          kotlin/ru/perm/v/shopkotlin/service/impl/GroupProductServiceTestIntegration.kt
//              :58:48 Unresolved reference. None of the following candidates is applicable because of receiver type mismatch:
//    public inline fun <T> Enumeration<TypeVariable(T)>.toList(): List<TypeVariable(T)> defined in kotlin.collections
//    public fun <T> Array<out TypeVariable(T)>.toList(): List<TypeVariable(T)> defined in kotlin.collections
//    public fun BooleanArray.toList(): List<Boolean> defined in kotlin.collections
//    public fun ByteArray.toList(): List<Byte> defined in kotlin.collections
//    public fun CharArray.toList(): List<Char> defined in kotlin.collections
//    public fun CharSequence.toList(): List<Char> defined in kotlin.text
//    public fun DoubleArray.toList(): List<Double> defined in kotlin.collections
    @Test
    fun demoQueryDslCheckIds() {
        val groupProductService = GroupProductServiceImpl(groupProductRepository, productService)

        val groups = groupProductService.repository.findAllByOrderByNAsc()

        val ids: MutableList<Long> = ArrayList()
        for (g in groups) {
            ids.add(g.n)
        }
//        val ids:List<Long> = groups.stream().map { it.n }.toList()

        assertContentEquals(listOf(1L, 2L, 3L, 4L, 5L, 6L, 7L), ids)
    }

    @Test
    fun getByDslFilter() {
        val groupProductService = GroupProductServiceImpl(groupProductRepository, productService)
        val filter = GroupProductFilter(listOf(1L, 2L))

        val filteredGroups = groupProductService.getByFilter(filter)

        assertEquals(2, filteredGroups.size)
        assertEquals(1L, filteredGroups.get(0).n)
        assertEquals(2L, filteredGroups.get(1).n)
    }

    @Test
    fun getByName() {
        val groupProductService = GroupProductServiceImpl(groupProductRepository, productService)
        val groups = groupProductService.findByNameContaining("Desktop")
        assertEquals(1, groups.size)
        assertEquals("Desktop Computers", groups.get(0).name)
    }

    @Test
    fun create() {
        val N = -1L
        val NAME = "NAME"
        val PARENT_N = 100L
        val groupProductDTO = GroupProductDTO(N, NAME, PARENT_N, false)
        val groupProductService = GroupProductServiceImpl(groupProductRepository, productService)

        val createdDTO = groupProductService.create(groupProductDTO)

        assertEquals(NAME, createdDTO.name)
        assertEquals(PARENT_N, createdDTO.parentN)
        assertFalse(createdDTO.haveChilds)
    }

    @Test
    fun getByN() {
        val N = 1L
        val groupProductService = GroupProductServiceImpl(groupProductRepository, productService)

        val groupDTO = groupProductService.getByN(N)

        assertEquals(N, groupDTO.n)
    }

    @Test
    fun existsByN() {
        val N = 1L
        val groupProductService = GroupProductServiceImpl(groupProductRepository, productService)

        assertTrue(groupProductService.existsByN(N))
    }

    @Test
    fun notExistsByN() {
        val N = 1000L
        val groupProductService = GroupProductServiceImpl(groupProductRepository, productService)

        assertFalse(groupProductService.existsByN(N))
    }

    @Test
    fun existProductsInGroup() {
        val GROUP_ID = 100L

        val groupProductService = GroupProductServiceImpl(groupProductRepository, productService)
        `when`(productService.getByGroupProductN(GROUP_ID)).thenReturn(listOf(ProductDTO(1L, "", GROUP_ID)))

        assertTrue(groupProductService.existProductsInGroup(GROUP_ID))
    }

    @Test
    fun notExistProductsInGroup() {
        val GROUP_ID = 100L

        val groupProductService = GroupProductServiceImpl(groupProductRepository, productService)
        `when`(productService.getByGroupProductN(GROUP_ID)).thenReturn(emptyList())

        assertFalse(groupProductService.existProductsInGroup(GROUP_ID))
    }

    @Test
    fun getSubGroups() {
        val GROUP_ID = 1L

        val groupProductService = GroupProductServiceImpl(groupProductRepository, productService)

        val subGroupIds = groupProductService.getSubGroups(GROUP_ID).map { it.n }

        assertTrue(subGroupIds.isNotEmpty())
        assertEquals(4, subGroupIds.size)

        // в kotlin можно так сравнивать списки
        assertEquals(listOf(2L, 5L, 6L, 7L), subGroupIds.toList())
        assertEquals(listOf(2L, 5L, 6L, 7L).toSet(), subGroupIds.toSet())
        // и даже так
        assertTrue(listOf(2L, 5L, 6L, 7L) == subGroupIds)
    }
}