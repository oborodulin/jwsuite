package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportHouseView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportHouse

class TerritoryReportHouseViewListToTerritoryReportHousesListMapper(mapper: TerritoryReportHouseViewToTerritoryReportHouseMapper) :
    ListMapperImpl<TerritoryReportHouseView, TerritoryReportHouse>(mapper)