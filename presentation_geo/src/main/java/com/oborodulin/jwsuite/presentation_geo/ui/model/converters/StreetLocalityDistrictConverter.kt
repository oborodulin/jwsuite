package com.oborodulin.jwsuite.presentation_geo.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetLocalityDistrictsForStreetUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetLocalityDistrictsUiModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictsListToLocalityDistrictsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetToStreetUiMapper

class StreetLocalityDistrictConverter(
    private val streetMapper: StreetToStreetUiMapper,
    private val localityDistrictsListMapper: LocalityDistrictsListToLocalityDistrictsListItemMapper
) : CommonResultConverter<GetLocalityDistrictsForStreetUseCase.Response, StreetLocalityDistrictsUiModel>() {
    override fun convertSuccess(data: GetLocalityDistrictsForStreetUseCase.Response) =
        StreetLocalityDistrictsUiModel(
            street = streetMapper.map(data.streetWithLocalityDistricts.street),
            localityDistricts = localityDistrictsListMapper.map(data.streetWithLocalityDistricts.localityDistricts)
        )

}