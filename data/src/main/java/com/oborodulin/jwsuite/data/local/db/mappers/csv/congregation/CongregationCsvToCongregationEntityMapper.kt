package com.oborodulin.jwsuite.data.local.db.mappers.csv.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationCsv
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity

class CongregationCsvToCongregationEntityMapper : Mapper<com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationCsv, CongregationEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationCsv) = CongregationEntity(
        congregationId = input.congregationId,
        congregationNum = input.congregationNum,
        congregationName = input.congregationName,
        territoryMark = input.territoryMark,
        isFavorite = input.isFavorite,
        lastVisitDate = input.lastVisitDate,
        cLocalitiesId = input.cLocalitiesId
    )
}