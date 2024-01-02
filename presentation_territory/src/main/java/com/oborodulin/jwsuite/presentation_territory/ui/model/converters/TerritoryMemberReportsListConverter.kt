package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetMemberReportsUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryMemberReportsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report.TerritoryMemberReportsListToTerritoryMemberReportsListItemMapper

class TerritoryMemberReportsListConverter(private val mapper: TerritoryMemberReportsListToTerritoryMemberReportsListItemMapper) :
    CommonResultConverter<GetMemberReportsUseCase.Response, List<TerritoryMemberReportsListItem>>() {
    override fun convertSuccess(data: GetMemberReportsUseCase.Response) =
        mapper.map(data.territoryMemberReports)
}