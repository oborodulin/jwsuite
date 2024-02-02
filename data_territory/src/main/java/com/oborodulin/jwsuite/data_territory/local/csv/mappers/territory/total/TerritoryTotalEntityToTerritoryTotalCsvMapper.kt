package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.total

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryTotalEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryTotalCsv

class TerritoryTotalEntityToTerritoryTotalCsvMapper :
    Mapper<TerritoryTotalEntity, TerritoryTotalCsv> {
    override fun map(input: TerritoryTotalEntity) = TerritoryTotalCsv(
        territoryTotalId = input.territoryTotalId,
        lastVisitDate = input.lastVisitDate,
        totalQty = input.totalQty,
        totalIssued = input.totalIssued,
        totalProcessed = input.totalProcessed,
        ttlCongregationsId = input.ttlCongregationsId
    )
}