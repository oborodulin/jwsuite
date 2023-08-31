package com.oborodulin.jwsuite.presentation_geo.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geolocalitydistrict.GetLocalityDistrictsUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictsListToLocalityDistrictsListItemMapper

class LocalityDistrictsListConverter(
    private val mapper: LocalityDistrictsListToLocalityDistrictsListItemMapper
) :
    CommonResultConverter<GetLocalityDistrictsUseCase.Response, List<LocalityDistrictsListItem>>() {
    override fun convertSuccess(data: GetLocalityDistrictsUseCase.Response) =
        mapper.map(data.localityDistricts)
}