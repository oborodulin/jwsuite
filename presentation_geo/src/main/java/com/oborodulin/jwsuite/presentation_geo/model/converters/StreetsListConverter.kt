package com.oborodulin.jwsuite.presentation_geo.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetStreetsUseCase
import com.oborodulin.jwsuite.presentation_geo.model.StreetsListItem
import com.oborodulin.jwsuite.presentation_geo.model.mappers.street.StreetsListToStreetsListItemMapper

class StreetsListConverter(private val mapper: StreetsListToStreetsListItemMapper) :
    CommonResultConverter<GetStreetsUseCase.Response, List<StreetsListItem>>() {
    override fun convertSuccess(data: GetStreetsUseCase.Response) =
        mapper.map(data.streets)
}