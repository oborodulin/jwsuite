package com.oborodulin.jwsuite.presentation_geo.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetMicrodistrictsForStreetUseCase
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetMicrodistrictUiModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictsListToMicrodistrictsListItemMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetToStreetUiMapper

class StreetMicrodistrictConverter(
    private val streetMapper: StreetToStreetUiMapper,
    private val microdistrictsListMapper: MicrodistrictsListToMicrodistrictsListItemMapper
) : CommonResultConverter<GetMicrodistrictsForStreetUseCase.Response, StreetMicrodistrictUiModel>() {
    override fun convertSuccess(data: GetMicrodistrictsForStreetUseCase.Response) =
        StreetMicrodistrictUiModel(
            street = streetMapper.map(data.streetWithMicrodistricts.street),
            microdistricts = microdistrictsListMapper.map(data.streetWithMicrodistricts.microdistricts)
        )

}