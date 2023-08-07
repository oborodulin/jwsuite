package com.oborodulin.jwsuite.presentation.ui.modules.geo.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionsUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.RegionsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.region.RegionsListToRegionsListItemMapper

class RegionsListConverter(private val mapper: RegionsListToRegionsListItemMapper) :
    CommonResultConverter<GetRegionsUseCase.Response, List<RegionsListItem>>() {
    override fun convertSuccess(data: GetRegionsUseCase.Response) =
        mapper.map(data.regions)
}