package ru.perm.v.shopkotlin.service.impl

import com.querydsl.core.BooleanBuilder
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.data.querydsl.QSort
import org.springframework.stereotype.Service
import ru.perm.v.shopkotlin.consts.ErrMessages
import ru.perm.v.shopkotlin.dto.GroupProductDTO
import ru.perm.v.shopkotlin.entity.GroupProductEntity
import ru.perm.v.shopkotlin.entity.QGroupProductEntity
import ru.perm.v.shopkotlin.filter.GroupProductFilter
import ru.perm.v.shopkotlin.repository.GroupProductRepository
import ru.perm.v.shopkotlin.service.GroupProductService
import ru.perm.v.shopkotlin.service.ProductService
import java.lang.String.format

/**
 * The Service will return the DTO. To get away from lazy.
 */
@Service
class GroupProductServiceImpl(
    @Lazy val repository: GroupProductRepository,
    @Lazy val productService: ProductService
) : GroupProductService {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)
    // @Lazy PROPER INITIALIZATION. NEED FOR SPRING.
    // Error: The dependencies of some of the beans in the application context form a cycle:
    // ProductService depends on GroupProductService AND GroupProductService depends on ProductService
    // see: https://www.baeldung.com/circular-dependencies-in-spring
    override fun create(groupProductDTO: GroupProductDTO): GroupProductDTO {
        logger.info(format("===================================create %s", groupProductDTO))
        logger.info(format(">>>>before count=%s",repository.count()));
        val groupForSave = GroupProductEntity(
            groupProductDTO.n,
            groupProductDTO.name,
            groupProductDTO.parentN,
            groupProductDTO.haveChilds)
        val savedGroup = repository.saveAndFlush(groupForSave)
        logger.info(format(">>>>after count=%s",repository.count()));
        return GroupProductDTO(
            savedGroup.n,
            savedGroup.name,
            savedGroup.parentN,
            savedGroup.haveChilds
        )
    }

    @Throws(Exception::class)
    override fun getByN(n: Long): GroupProductDTO {
        val groupProductEntityOptional = repository.findById(n)
        if (groupProductEntityOptional.isPresent) {
            val groupProductEntity = groupProductEntityOptional.get()
            return GroupProductDTO(
                groupProductEntity.n,
                groupProductEntity.name,
                groupProductEntity.parentN,
                groupProductEntity.haveChilds
            )
        } else {
            throw Exception(String.format(ErrMessages.NOT_FOUND_GROUP_BY_ID, n))
        }
        //NOTE: В kotlin, при проверке на существование, можно короче, через '?'. Так:
        // val groupProductEntity = repository.getByN(n)?: throw Exception(String.format("GroupProduct with n=%s not exist ", n))
// В java так
//        if (groupProductEntity == null) {
//            throw Exception(String.format("GroupProduct with n=%s not exist ", n))
//        }
    }

    override fun existsByN(n: Long): Boolean {
        return repository.existsByN(n)
    }

    override fun getByFilter(filter: GroupProductFilter): List<GroupProductDTO> {
        var booleanBuilder = BooleanBuilder()

        if (!filter.listN.isEmpty()) {
            booleanBuilder = booleanBuilder.and(QGroupProductEntity.groupProductEntity.n.`in`(filter.listN))
        }
        if (!filter.name.isEmpty()) {
            booleanBuilder =
                booleanBuilder.and(QGroupProductEntity.groupProductEntity.name.like("%" + filter.name + "%"))
        }

        // example sort
        // val entities = repository.findAll(booleanBuilder, QSort.by("name", "n"))
        val entities = repository.findAll(booleanBuilder, QSort.by(filter.listSortBy.joinToString(",")))

        val dtos = entities.map { GroupProductDTO(it.n, it.name, it.parentN, it.haveChilds) }
        return dtos
    }

    override fun existProductsInGroup(n: Long): Boolean {
        return !productService.getByGroupProductN(n).isEmpty()
    }

    override fun getSubGroups(n: Long): List<GroupProductDTO> {
        val subGroups = repository.findAllByParentN(n)
        val ret = ArrayList<GroupProductDTO>()
        for (subGroup in subGroups) {
            ret.add(GroupProductDTO(subGroup.n, subGroup.name, subGroup.parentN, subGroup.haveChilds))
        }
        return ret
    }

    override fun findAllByOrderByNAsc(): List<GroupProductDTO> {
        return repository.findAllByOrderByNAsc()
            .map { g -> GroupProductDTO(g.n, g.name, g.parentN, g.haveChilds) }//.toList()
    }

    override fun findByNameContaining(name: String): List<GroupProductDTO> {
        return repository.findByNameContaining(name)
            .map { g -> GroupProductDTO(g.n, g.name, g.parentN, g.haveChilds) }//.toList()
    }

    // Можно совместить с create() или даже сделать один save(). Но сделал именно так.
    @Throws(Exception::class)
    override fun update(groupProductDTO: GroupProductDTO): GroupProductDTO {
        if (!existsByN(groupProductDTO.n)) {
            throw Exception(String.format(ErrMessages.NOT_FOUND_GROUP_BY_ID, groupProductDTO.n))
        }
        val groupProductEntity = GroupProductEntity(
            groupProductDTO.n, groupProductDTO.name, groupProductDTO.parentN, groupProductDTO.haveChilds
        )
        val newGroup = repository.save(groupProductEntity)
        return GroupProductDTO(
            newGroup.n,
            newGroup.name,
            newGroup.parentN,
            newGroup.haveChilds
        )
    }

    @Throws(Exception::class)
    override fun deleteByN(n: Long) {
        if (!existsByN(n)) {
            throw Exception(String.format(ErrMessages.NOT_FOUND_GROUP_BY_ID, n))
        }
        repository.deleteByN(n)
    }
}