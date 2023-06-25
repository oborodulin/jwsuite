package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationUseCase
import com.oborodulin.jwsuite.presentation.ui.model.mappers.region.RegionToRegionUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationToCongregationUiMapper

class RegionConverter(
    private val mapper: RegionToRegionUiMapper
) :
    CommonResultConverter<GetCongregationUseCase.Response, CongregationUi>() {
    override fun convertSuccess(data: GetCongregationUseCase.Response) =
        mapper.map(data.congregation)
}