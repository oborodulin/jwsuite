package com.oborodulin.jwsuite.presentation_geo.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.GetMicrodistrictsUseCase
import com.oborodulin.jwsuite.presentation_geo.model.MicrodistrictsListItem
import com.oborodulin.jwsuite.presentation_geo.model.mappers.microdistrict.MicrodistrictsListToMicrodistrictsListItemMapper

class MicrodistrictsListConverter(private val mapper: MicrodistrictsListToMicrodistrictsListItemMapper) :
    CommonResultConverter<GetMicrodistrictsUseCase.Response, List<MicrodistrictsListItem>>() {
    override fun convertSuccess(data: GetMicrodistrictsUseCase.Response) =
        mapper.map(data.microdistricts)
}