package com.oborodulin.jwsuite.presentation_geo.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.georegion.SaveRegionUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionToRegionUiMapper

class SaveRegionConverter(private val mapper: RegionToRegionUiMapper) :
    CommonResultConverter<SaveRegionUseCase.Response, RegionUi>() {
    override fun convertSuccess(data: SaveRegionUseCase.Response) = mapper.map(data.region)
}