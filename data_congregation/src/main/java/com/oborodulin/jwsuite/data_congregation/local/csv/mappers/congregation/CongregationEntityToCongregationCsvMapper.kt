package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationCsv

class CongregationEntityToCongregationCsvMapper : Mapper<CongregationEntity, CongregationCsv> {
    override fun map(input: CongregationEntity) = CongregationCsv(
        congregationId = input.congregationId,
        congregationNum = input.congregationNum,
        congregationName = input.congregationName,
        territoryMark = input.territoryMark,
        isFavorite = input.isFavorite,
        lastVisitDate = input.lastVisitDate,
        cLocalitiesId = input.cLocalitiesId
    )
}