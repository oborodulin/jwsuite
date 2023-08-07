package com.oborodulin.jwsuite.presentation.ui.modules.geo.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geomicrodistrict.GetMicrodistrictUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.MicrodistrictUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.microdistrict.MicrodistrictToMicrodistrictUiMapper

class MicrodistrictConverter(private val mapper: MicrodistrictToMicrodistrictUiMapper) :
    CommonResultConverter<GetMicrodistrictUseCase.Response, MicrodistrictUi>() {
    override fun convertSuccess(data: GetMicrodistrictUseCase.Response) =
        mapper.map(data.microdistrict)
}