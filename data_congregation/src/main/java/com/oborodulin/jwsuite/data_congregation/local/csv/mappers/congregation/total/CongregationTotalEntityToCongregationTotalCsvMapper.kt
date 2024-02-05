package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.total

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationTotalCsv

class CongregationTotalEntityToCongregationTotalCsvMapper :
    Mapper<CongregationTotalEntity, CongregationTotalCsv> {
    override fun map(input: CongregationTotalEntity) = CongregationTotalCsv(
        congregationTotalId = input.congregationTotalId,
        lastVisitDate = input.lastVisitDate,
        totalMembers = input.totalMembers,
        totalFulltimeMembers = input.totalFulltimeMembers,
        totalTerritories = input.totalTerritories,
        totalTerritoryIssued = input.totalTerritoryIssued,
        totalTerritoryProcessed = input.totalTerritoryProcessed,
        ctlCongregationsId = input.ctlCongregationsId
    )
}