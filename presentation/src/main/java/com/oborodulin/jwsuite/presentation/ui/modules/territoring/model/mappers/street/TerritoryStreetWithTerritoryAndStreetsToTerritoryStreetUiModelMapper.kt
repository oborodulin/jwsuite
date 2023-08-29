package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.street

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.TerritoryStreetWithTerritoryAndStreets
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.street.StreetsListToStreetsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryStreetUiModel
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryToTerritoryUiMapper

class TerritoryStreetWithTerritoryAndStreetsToTerritoryStreetUiModelMapper(
    private val territoryStreetMapper: TerritoryStreetToTerritoryStreetUiMapper,
    private val territoryMapper: TerritoryToTerritoryUiMapper,
    private val streetListItemMapper: StreetsListToStreetsListItemMapper
) :
    Mapper<TerritoryStreetWithTerritoryAndStreets, TerritoryStreetUiModel> {
    override fun map(input: TerritoryStreetWithTerritoryAndStreets): TerritoryStreetUiModel {
        return TerritoryStreetUiModel(
            territoryStreet = territoryStreetMapper.map(input.territoryStreet),
            territory = territoryMapper.map(input.territory),
            streets = streetListItemMapper.map(input.streets)
        )
    }
}