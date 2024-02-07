package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.member

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryMemberCrossRefCsv

class TerritoryMemberCrossRefCsvListToTerritoryMemberCrossRefEntityListMapper(mapper: TerritoryMemberCrossRefCsvToTerritoryMemberCrossRefEntityMapper) :
    ListMapperImpl<TerritoryMemberCrossRefCsv, TerritoryMemberCrossRefEntity>(mapper)