package com.oborodulin.jwsuite.presentation.ui.modules.geo.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetRegionDistrictsUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.RegionDistrictsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.regiondistrict.RegionDistrictsListToRegionDistrictsListItemMapper

class RegionDistrictsListConverter(
    private val mapper: RegionDistrictsListToRegionDistrictsListItemMapper
) :
    CommonResultConverter<GetRegionDistrictsUseCase.Response, List<RegionDistrictsListItem>>() {
    override fun convertSuccess(data: GetRegionDistrictsUseCase.Response) =
        mapper.map(data.regionDistricts)
}