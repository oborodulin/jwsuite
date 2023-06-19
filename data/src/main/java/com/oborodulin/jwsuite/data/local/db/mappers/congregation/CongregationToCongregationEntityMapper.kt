package com.oborodulin.jwsuite.data.local.db.mappers.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.domain.model.Congregation
import java.util.UUID

class CongregationToCongregationEntityMapper : Mapper<Congregation, CongregationEntity> {
    override fun map(input: Congregation) = CongregationEntity(
        congregationId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        congregationNum = input.congregationNum,
        congregationName = input.congregationName,
        territoryMark = input.territoryMark,
        isFavorite = input.isFavorite,
        localitiesId = input.locality.id!!
    )
}