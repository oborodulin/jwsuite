package com.oborodulin.jwsuite.presentation.ui.modules.geo.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.geostreet.GetStreetsForTerritoryUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.StreetsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.street.StreetsListToStreetsListItemMapper

class StreetsForTerritoryListConverter(private val mapper: StreetsListToStreetsListItemMapper) :
    CommonResultConverter<GetStreetsForTerritoryUseCase.Response, List<StreetsListItem>>() {
    override fun convertSuccess(data: GetStreetsForTerritoryUseCase.Response) =
        mapper.map(data.streets)
}