package com.oborodulin.jwsuite.presentation_territory.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.territory.report.GetMemberReportUseCase
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryMemberReportUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.report.TerritoryMemberReportToTerritoryMemberReportUiMapper

class TerritoryMemberReportConverter(private val mapper: TerritoryMemberReportToTerritoryMemberReportUiMapper) :
    CommonResultConverter<GetMemberReportUseCase.Response, TerritoryMemberReportUi>() {
    override fun convertSuccess(data: GetMemberReportUseCase.Response) =
        mapper.map(data.territoryMemberReport)
}