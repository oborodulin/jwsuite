package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.congregation

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.CongregationTerritoryCrossRefCsv

class CongregationTerritoryCrossRefCsvListToCongregationTerritoryCrossRefEntityListMapper(mapper: CongregationTerritoryCrossRefCsvToCongregationTerritoryCrossRefEntityMapper) :
    ListMapperImpl<CongregationTerritoryCrossRefCsv, CongregationTerritoryCrossRefEntity>(mapper)