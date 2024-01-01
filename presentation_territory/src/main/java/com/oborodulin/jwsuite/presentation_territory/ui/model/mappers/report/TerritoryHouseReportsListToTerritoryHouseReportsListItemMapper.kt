package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.territory.TerritoryReportHouse
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryReportHousesListItem

class TerritoryHouseReportsListToTerritoryHouseReportsListItemMapper(mapper: TerritoryHouseReportToTerritoryHouseReportsListItemMapper) :
    ListMapperImpl<TerritoryReportHouse, TerritoryReportHousesListItem>(mapper)