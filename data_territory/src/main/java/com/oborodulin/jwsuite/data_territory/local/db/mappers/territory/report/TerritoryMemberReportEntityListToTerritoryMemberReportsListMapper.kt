package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport

class TerritoryMemberReportEntityListToTerritoryMemberReportsListMapper(mapper: TerritoryMemberReportEntityToTerritoryMemberReportMapper) :
    ListMapperImpl<TerritoryMemberReportEntity, TerritoryMemberReport>(mapper)