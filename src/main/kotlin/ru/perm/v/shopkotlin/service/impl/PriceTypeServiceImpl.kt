package ru.perm.v.shopkotlin.service.impl

import org.springframework.stereotype.Service
import ru.perm.v.shopkotlin.dto.PriceTypeDTO
import ru.perm.v.shopkotlin.entity.PriceTypeEntity
import ru.perm.v.shopkotlin.repository.PriceTypeRepository
import ru.perm.v.shopkotlin.service.PriceTypeService

@Service
class PriceTypeServiceImpl(val priceTypeRepository: PriceTypeRepository) : PriceTypeService {

    override fun create(dto: PriceTypeDTO): PriceTypeDTO {
        val created=priceTypeRepository.save(PriceTypeEntity(dto.n, dto.name))
        return PriceTypeDTO(created.n, created.name)
    }

    override fun findAllByOrderByNAsc(): List<PriceTypeDTO> {
        return priceTypeRepository.findAllByOrderByNAsc()
            .map { e -> PriceTypeDTO(e.n, e.name) }
    }

}