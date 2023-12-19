package ru.perm.v.shopkotlin.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.perm.v.shopkotlin.consts.ErrMessages
import ru.perm.v.shopkotlin.consts.ErrMessages.Companion.HAVE_PRODUCTS
import ru.perm.v.shopkotlin.consts.ErrMessages.Companion.HAVE_SUBGROUPS
import ru.perm.v.shopkotlin.consts.ErrMessages.Companion.NOT_FOUND_GROUP_BY_ID
import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.service.GroupProductService
import ru.perm.v.shopkotlin.service.ProductService
import java.lang.String.format
import javax.validation.ConstraintViolation
import javax.validation.Valid
import javax.validation.Validation

@RestController
@RequestMapping("/group_product")
@Api(tags = ["group-product-rest"])
@Validated
/**
 * Определяет ТОЛЬКО интерфейсы доступа к сервису. Маппинг в DTO делается в сервисе
 * (уход от lazy проблем, независимость от способа получения самих DTO и т.п.).
 */

//TODO: check work cache
class GroupProductRest(val groupProductService: GroupProductService, val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)
    val validator: javax.validation.Validator = Validation.buildDefaultValidatorFactory().validator

    @GetMapping("/echo/{mes}")
    @ApiOperation("Simple echo test")
    fun echoMessage(
        @Parameter(
            description = "Any string. will be returned in response."
        )
        @PathVariable mes: String
    ): String {
        return mes
    }

    @PostMapping
    @ApiOperation("Create GroupProduct from DTO")
    fun create(
        @Parameter(
            description = "DTO of GroupProduct."
        )
        @RequestBody groupProductDTO: GroupProductDTO
    ): GroupProductDTO {
        val violations: MutableSet<ConstraintViolation<GroupProductDTO>> =
            validator.validate(groupProductDTO)

        if (violations.size > 0) {
            var messageError = ""
// OLD STYLE
//            for(violation in violations) {
//                messageError = messageError.plus(violation.message + "\n")
//            }

// USED STREAM
            violations.forEach { violation ->
                messageError = messageError.plus(violation.message + "\n")
            }
            throw Exception("Group product with n: $groupProductDTO has errors: $messageError")
        }
        return groupProductService.create(groupProductDTO)
    }

    @GetMapping("/{n}")
    @Cacheable("group_products")
    @ApiOperation("Get GroupProduct by ID")
    fun getById(
        @Parameter(
            description = "ID of GroupProduct."
        )
        @PathVariable n: Long
    ): GroupProductDTO {
        if (n < 0) {
            throw Exception(ErrMessages.ID_MUST_POSITIVE)
        }
        try {
            return groupProductService.getByN(n)
        } catch (excpt: Exception) {
            throw Exception(excpt.message)
        }
    }

    /**
     * get all groups product
     */
    @GetMapping("/")
    @Cacheable("allGroupProductDTO")
    @ApiOperation("Get all groups of product")
    fun all(): List<GroupProductDTO> {
        logger.info("GET all GroupProductDTO")
        val groups: List<GroupProductDTO> = groupProductService.findAllByOrderByNAsc()
        if (groups.isEmpty()) {
            throw Exception(ErrMessages.NOT_FOUND_ANY_GROUP)
        }
        return groups.map { entity ->
            GroupProductDTO(entity.n, entity.name, entity.parentN, entity.haveChilds)
        }
//        Error: .toList() лишний. Уже в list после map. Похоже особенности kotlin.
//        Нужно groups заколлектить в List и уже после как обычно маппить
//        взято отсюда https://www.baeldung.com/java-stream-immutable-collection.
//        Хрень в ".collect(toList())" он же вроде и так List
//        return groups.stream()
//            .map { entity ->
//                GroupProductDTO(entity.n, entity.name, entity.parentN, entity.haveChilds)
//            }.toList()
    }

    @GetMapping("/clear_cache")
    @CacheEvict("group_products", allEntries = true)
    @ApiOperation("Clear cache application")
    fun clearCache(): String {
        return "cleared"
    }

    @GetMapping("/find")
    @ApiOperation("Find groups by name")
    //TODO add Cache
    fun findByName(
        @Parameter(
            description = "Name of GroupProduct."
        )
        @Valid name: String
    ): List<GroupProductDTO> {
//        toList() (как в java)  в Kotlin не нужен.
//        val groups = service.findByNameContaining(name).toList()
        val groups = groupProductService.findByNameContaining(name)
        if (groups.isEmpty()) {
            throw Exception(format(ErrMessages.NOT_FOUND_GROUP_BY_NAME, name))
        }
        return groups.map { entity ->
            GroupProductDTO(entity.n, entity.name, entity.parentN, entity.haveChilds)
        }
    }

    @GetMapping("/{n}/subgroups")
    @ApiOperation("Get subgroups")
    @Cacheable("allGroupProductDTO")
    fun getSubGroups(
        @Parameter(
            description = "ID of GroupProduct."
        )
        @PathVariable n: Long
    ): List<GroupProductDTO> {
        if (!groupProductService.existsByN(n)) {
            throw Exception("Group product not found with id: $n")
        }
        val subGroups = groupProductService.getSubGroups(n)
        return subGroups.map { entity ->
            GroupProductDTO(entity.n, entity.name, entity.parentN, entity.haveChilds)
        }
    }

    @DeleteMapping("/{n}")
    @ApiOperation("Delete GroupProduct by ID")
    @CacheEvict("allGroupProductDTO", key = "#n")
    fun deleteByN(
        @Parameter(
            description = "ID of GroupProduct."
        )
        @PathVariable n: Long
    ) {
        if (!groupProductService.existsByN(n)) {
            throw Exception(format(NOT_FOUND_GROUP_BY_ID, n))
        }
        if (!groupProductService.getSubGroups(n).isEmpty()) {
            throw Exception(format(HAVE_SUBGROUPS, n))
        }
        if (productService.getByGroupProductN(n).isNotEmpty()) {
            throw Exception(format(HAVE_PRODUCTS, n))
        }
        groupProductService.deleteByN(n)
    }
}