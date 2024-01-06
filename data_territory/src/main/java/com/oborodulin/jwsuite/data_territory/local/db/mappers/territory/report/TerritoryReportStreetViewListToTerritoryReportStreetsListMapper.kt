package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.report

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportStreetView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportStreet

class TerritoryReportStreetViewListToTerritoryReportStreetsListMapper(mapper: TerritoryReportStreetViewToTerritoryReportStreetMapper) :
    ListMapperImpl<TerritoryReportStreetView, TerritoryReportStreet>(mapper)