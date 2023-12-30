package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryHouseReportView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryHouseReport

class TerritoryHouseReportViewListToTerritoryHouseReportsListMapper(mapper: TerritoryHouseReportViewToTerritoryHouseReportMapper) :
    ListMapperImpl<TerritoryHouseReportView, TerritoryHouseReport>(mapper)