package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.total

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationTotalCsv

class CongregationTotalCsvToCongregationTotalEntityMapper :
    Mapper<CongregationTotalCsv, CongregationTotalEntity> {
    override fun map(input: CongregationTotalCsv) = CongregationTotalEntity(
        congregationTotalId = input.congregationTotalId,
        lastVisitDate = input.lastVisitDate,
        totalMembers = input.totalMembers,
        totalFulltimeMembers = input.totalFulltimeMembers,
        ctlCongregationsId = input.ctlCongregationsId
    )
}