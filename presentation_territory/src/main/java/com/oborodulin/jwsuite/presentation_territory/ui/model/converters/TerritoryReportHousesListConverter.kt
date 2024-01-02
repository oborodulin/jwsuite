package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetReportHousesUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryReportHousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report.TerritoryReportHousesListToTerritoryReportHousesListItemMapper

class TerritoryReportHousesListConverter(private val mapper: TerritoryReportHousesListToTerritoryReportHousesListItemMapper) :
    CommonResultConverter<GetReportHousesUseCase.Response, List<TerritoryReportHousesListItem>>() {
    override fun convertSuccess(data: GetReportHousesUseCase.Response) =
        mapper.map(data.territoryReportHouses)
}