package com.oborodulin.jwsuite.data_territory.local.db.mappers.territoryreport

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberReportView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryMemberReport

class TerritoryMemberReportViewListToTerritoryMemberReportsListMapper(mapper: TerritoryMemberReportViewToTerritoryMemberReportMapper) :
    ListMapperImpl<TerritoryMemberReportView, TerritoryMemberReport>(mapper)