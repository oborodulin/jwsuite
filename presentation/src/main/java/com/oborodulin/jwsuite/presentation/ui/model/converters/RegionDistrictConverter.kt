package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetRegionDistrictUseCase
import com.oborodulin.jwsuite.presentation.ui.model.RegionDistrictUi
import com.oborodulin.jwsuite.presentation.ui.model.mappers.regiondistrict.RegionDistrictToRegionDistrictUiMapper

class RegionDistrictConverter(private val mapper: RegionDistrictToRegionDistrictUiMapper) :
    CommonResultConverter<GetRegionDistrictUseCase.Response, RegionDistrictUi>() {
    override fun convertSuccess(data: GetRegionDistrictUseCase.Response) =
        mapper.nullableMap(data.regionDistrict)
}