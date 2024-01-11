package ru.perm.v.shopkotlin.rest

import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Parameter
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.service.ProductService
import java.lang.String.format
import javax.validation.ConstraintViolation
import javax.validation.Validation

@RestController
@RequestMapping("/product")
class ProductRest(val productService: ProductService) {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    val validator: javax.validation.Validator = Validation.buildDefaultValidatorFactory().validator

    @GetMapping("/count_names")
    @ApiOperation("Get number of product NAMES")
    fun getCountOfProductNames(): Long {
        return productService.getCountOfProductNames()
    }

    @GetMapping("/echo/{mes}")
    @ApiOperation("Simple echo test")
    @Cacheable("product_echo")
    fun echoMessage(
        @Parameter(
            description = "Any string. will be returned in response."
        )
        @PathVariable mes: String
    ): String {
        logger.info(format("echo %s",mes))
        return mes
    }

    @PostMapping
    @ApiOperation("Create Product from DTO")
    fun create(
        @Parameter(
            description = "DTO of Product."
        )
        @RequestBody productDTO: ProductDTO
    ): ProductDTO {
        validate(productDTO)
        return productService.create(productDTO)
    }

    @GetMapping("/{n}")
    @ApiOperation("Get Product by N")
    @Cacheable("products")
    fun getByN(
        @Parameter(
            description = "N(ID) Product."
        )
        @PathVariable
        n: Long
    ): ProductDTO {
        return productService.getByN(n)
    }

    @GetMapping("/")
    @ApiOperation("Get all products")
    @Cacheable("products")
    fun getAll(): List<ProductDTO> {
        return productService.getAll()
    }

    @PostMapping(path = ["/{n}"], consumes = ["application/json"], produces = ["application/json"])
//    @CacheEvict("products")
    @CacheEvict(value = ["products", "allGroupProductDTO"], allEntries = true)
//    @CacheEvict(value = ["product"], key = "#product.n")
    @ApiOperation("Update Product")
    fun update(
        @PathVariable
        n: Long,
        @Parameter(
            description = "Product."
        )
        @RequestBody
        product: ProductDTO
    ): ProductDTO {
        logger.info("UPDATE:$product")
        validate(product)
        return productService.update(product)
    }

    @DeleteMapping("/{n}")
    @CacheEvict(value = ["products"], key = "#n") // no error, variant 1
//    @CacheEvict(cacheNames = ["products"], key = "#n") // no error, variant 2
    fun deleteById(
        @Parameter(
            description = "N(ID) Product."
        )
        @PathVariable
        n: Long
    ) {
        productService.delete(n)
    }

    fun validate(productDTO: ProductDTO) {
        val violations: MutableSet<ConstraintViolation<ProductDTO>> =
            validator.validate(productDTO)

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
            throw Exception("$productDTO has errors: $messageError")
        }
    }

}