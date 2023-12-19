package ru.perm.v.shopkotlin.service.impl

import com.querydsl.core.BooleanBuilder
import org.springframework.data.querydsl.QSort
import org.springframework.stereotype.Service
import ru.perm.v.shopkotlin.consts.ErrMessages
import ru.perm.v.shopkotlin.dto.ProductDTO
import ru.perm.v.shopkotlin.entity.ProductEntity
import ru.perm.v.shopkotlin.entity.QProductEntity
import ru.perm.v.shopkotlin.filter.ProductFilter
import ru.perm.v.shopkotlin.repository.ProductRepository
import ru.perm.v.shopkotlin.service.GroupProductService
import ru.perm.v.shopkotlin.service.ProductService


// On "./gradlew bootRun" for this code have ERROR:
//APPLICATION FAILED TO START
//***************************
//
//Description:
//
//The dependencies of some of the beans in the application context form a cycle:
//
//groupProductRest defined in file [/home/vasi/prog/kotlin/shop/shop_kotlin/build/classes/kotlin/main/ru/perm/v/shopkotlin/rest/GroupProductRest.class]
//┌─────┐
//|  groupProductServiceImpl defined in file [/home/vasi/prog/kotlin/shop/shop_kotlin/build/classes/kotlin/main/ru/perm/v/shopkotlin/service/impl/GroupProductServiceImpl.class]
//↑     ↓
//|  productServiceImpl defined in file [/home/vasi/prog/kotlin/shop/shop_kotlin/build/classes/kotlin/main/ru/perm/v/shopkotlin/service/impl/ProductServiceImpl.class]
//└─────┘

//@Service
//class ProductServiceImpl(
//    val repository: ProductRepository,
//    val  groupService: GroupProductService
//) :ProductService {
// That's why I do it this way:
@Service
class ProductServiceImpl : ProductService {

    lateinit var repository: ProductRepository;
    lateinit var groupService: GroupProductService;

    /**
     * ID in input dto will be ignored. ID will calculate
     */
    @Throws(Exception::class)
    override fun create(dto: ProductDTO): ProductDTO {
        if (!groupService.existsByN(dto.groupDtoN)) {
            throw Exception(String.format("GroupProduct with n=%s not exist", dto.groupDtoN))
        }
        val n = repository.getNextN()
        val savedProduct = repository.save(ProductEntity(n, dto.name, dto.groupDtoN))
        return ProductDTO(savedProduct.n, savedProduct.name, savedProduct.groupProductN)
    }

    override fun getByGroupProductN(n: Long): List<ProductDTO> {
        // В Java сделал бы так
        //        return repository.findAllByGroupProductNOrderByNAsc(n).stream()
        //            .map { ProductDTO(it.n, it.name, it.groupProductN) }.toList()
        // В kotlin можно проще. без .toList()
        return repository.findAllByGroupProductNOrderByNAsc(n)
            .map { ProductDTO(it.n, it.name, it.groupProductN) }
    }

    @Throws(Exception::class)
    override fun getByN(n: Long): ProductDTO {
        val product = repository.findById(n)
        if (product.isPresent) {
            val entity = product.get()
            return ProductDTO(entity.n, entity.name, entity.groupProductN)
        } else {
            throw Exception(String.format(ErrMessages.NOT_FOUND_PRODUCT_BY_ID, n))
        }
    }

    override fun getByNs(ids: List<Long>): List<ProductDTO> {
        return repository.findAllById(ids).map { ProductDTO(it.n, it.name, it.groupProductN) }
    }


    override fun getByFilter(filter: ProductFilter): List<ProductDTO> {
        var booleanBuilder = BooleanBuilder()

        if (!filter.listN.isEmpty()) {
            booleanBuilder = booleanBuilder.and(QProductEntity.productEntity.n.`in`(filter.listN))
        }
        if (!filter.name.isEmpty()) {
            booleanBuilder = booleanBuilder.and(QProductEntity.productEntity.name.like("%" + filter.name + "%"))
        }

        // example sort
        // val entities = repository.findAll(booleanBuilder, QSort.by("name", "n"))
        val entities = repository.findAll(booleanBuilder, QSort.by(filter.listSortBy.joinToString(",")))

        val dtos = entities.map { ProductDTO(it.n, it.name, it.groupProductN) }
        return dtos
    }

    override fun getByName(name: String): List<ProductDTO> {
        return repository.findByNameContainingOrderByName(name)
            .map { ProductDTO(it.n, it.name, it.groupProductN) }

    }

    override fun getCountOfProductNames(): Long {
        return repository.getCountOfProductNames()
    }

    override fun getAll(): List<ProductDTO> {
        return repository.findAllByOrderByNAsc()
            .map { ProductDTO(it.n, it.name, it.groupProductN) }
    }

    override fun existByN(n: Long): Boolean {
        return repository.findById(n).isPresent
    }

    @Throws(Exception::class)
    override fun update(dto: ProductDTO): ProductDTO {
        if (!groupService.existsByN(dto.groupDtoN)) {
            throw Exception(String.format("GroupProduct with n=%s not exist", dto.groupDtoN))
        }
        if (!existByN(dto.n)) {
            throw Exception(String.format("Product with n=%s not exist", dto.n))
        }
        val savedProduct = repository.save(ProductEntity(dto.n, dto.name, dto.groupDtoN))
        return ProductDTO(savedProduct.n, savedProduct.name, savedProduct.groupProductN)
    }

    @Throws(Exception::class)
    override fun delete(n: Long) {
        if (!existByN(n)) {
            throw Exception(String.format("Product with n=%s not exist", n))
        }
        repository.deleteById(n)
    }
}