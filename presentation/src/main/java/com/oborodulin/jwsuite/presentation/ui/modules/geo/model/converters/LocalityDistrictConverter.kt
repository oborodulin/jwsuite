package com.oborodulin.jwsuite.presentation.ui.modules.geo.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.GetLocalityDistrictUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityDistrictUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictUiMapper

class LocalityDistrictConverter(private val mapper: LocalityDistrictToLocalityDistrictUiMapper) :
    CommonResultConverter<GetLocalityDistrictUseCase.Response, LocalityDistrictUi>() {
    override fun convertSuccess(data: GetLocalityDistrictUseCase.Response) =
        mapper.map(data.localityDistrict)
}