package com.oborodulin.jwsuite.data.local.db.mappers.csv.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationCsv
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity

class CongregationEntityToCongregationCsvMapper : Mapper<CongregationEntity, com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationCsv> {
    override fun map(input: CongregationEntity) =
        com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationCsv(
            congregationId = input.congregationId,
            congregationNum = input.congregationNum,
            congregationName = input.congregationName,
            territoryMark = input.territoryMark,
            isFavorite = input.isFavorite,
            lastVisitDate = input.lastVisitDate,
            cLocalitiesId = input.cLocalitiesId
        )
}