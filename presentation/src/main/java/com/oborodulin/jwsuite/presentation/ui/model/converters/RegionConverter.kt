package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionUseCase
import com.oborodulin.jwsuite.presentation.ui.model.RegionUi
import com.oborodulin.jwsuite.presentation.ui.model.mappers.region.RegionToRegionUiMapper

class RegionConverter(private val mapper: RegionToRegionUiMapper) :
    CommonResultConverter<GetRegionUseCase.Response, RegionUi>() {
    override fun convertSuccess(data: GetRegionUseCase.Response) =
        mapper.map(data.region)
}