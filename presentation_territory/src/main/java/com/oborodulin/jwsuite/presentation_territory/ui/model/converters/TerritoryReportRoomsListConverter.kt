package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetReportRoomsUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryReportRoomsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report.TerritoryReportRoomsListToTerritoryReportRoomsListItemMapper

class TerritoryReportRoomsListConverter(private val mapper: TerritoryReportRoomsListToTerritoryReportRoomsListItemMapper) :
    CommonResultConverter<GetReportRoomsUseCase.Response, List<TerritoryReportRoomsListItem>>() {
    override fun convertSuccess(data: GetReportRoomsUseCase.Response) =
        mapper.map(data.territoryReportRooms)
}