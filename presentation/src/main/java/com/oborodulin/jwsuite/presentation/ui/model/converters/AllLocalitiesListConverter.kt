package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetAllLocalitiesUseCase
import com.oborodulin.jwsuite.presentation.ui.model.LocalitiesListItem
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalitiesListToLocalityListItemMapper

class AllLocalitiesListConverter(private val mapper: LocalitiesListToLocalityListItemMapper) :
    CommonResultConverter<GetAllLocalitiesUseCase.Response, List<LocalitiesListItem>>() {
    override fun convertSuccess(data: GetAllLocalitiesUseCase.Response) =
        mapper.map(data.localities)
}