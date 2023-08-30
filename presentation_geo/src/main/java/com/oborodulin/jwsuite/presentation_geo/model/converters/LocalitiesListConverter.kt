package com.oborodulin.jwsuite.presentation_geo.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalitiesUseCase
import com.oborodulin.jwsuite.presentation_geo.model.LocalitiesListItem
import com.oborodulin.jwsuite.presentation_geo.model.mappers.locality.LocalitiesListToLocalityListItemMapper

class LocalitiesListConverter(private val mapper: LocalitiesListToLocalityListItemMapper) :
    CommonResultConverter<GetLocalitiesUseCase.Response, List<LocalitiesListItem>>() {
    override fun convertSuccess(data: GetLocalitiesUseCase.Response) =
        mapper.map(data.localities)
}