package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.congregation

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.CongregationTerritoryCrossRefCsv

class CongregationTerritoryCrossRefEntityToCongregationTerritoryCrossRefCsvMapper :
    Mapper<CongregationTerritoryCrossRefEntity, CongregationTerritoryCrossRefCsv> {
    override fun map(input: CongregationTerritoryCrossRefEntity) = CongregationTerritoryCrossRefCsv(
        congregationTerritoryId = input.congregationTerritoryId,
        startUsingDate = input.startUsingDate,
        endUsingDate = input.endUsingDate,
        ctTerritoriesId = input.ctTerritoriesId,
        ctCongregationsId = input.ctCongregationsId
    )
}