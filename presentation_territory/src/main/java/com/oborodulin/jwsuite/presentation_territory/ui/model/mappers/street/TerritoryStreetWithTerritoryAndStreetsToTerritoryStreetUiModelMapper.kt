package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.TerritoryStreetWithTerritoryAndStreets
import com.oborodulin.jwsuite.presentation_territory.ui.modules.geo.model.mappers.street.StreetsListToStreetsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUiModel
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper

class TerritoryStreetWithTerritoryAndStreetsToTerritoryStreetUiModelMapper(
    private val territoryStreetMapper: TerritoryStreetToTerritoryStreetUiMapper,
    private val territoryMapper: TerritoryToTerritoryUiMapper,
    private val streetListItemMapper: StreetsListToStreetsListItemMapper
) : Mapper<TerritoryStreetWithTerritoryAndStreets, TerritoryStreetUiModel> {
    override fun map(input: TerritoryStreetWithTerritoryAndStreets): TerritoryStreetUiModel {
        return TerritoryStreetUiModel(
            territoryStreet = territoryStreetMapper.nullableMap(input.territoryStreet)
                ?: TerritoryStreetUi(),
            territory = territoryMapper.map(input.territory),
            streets = streetListItemMapper.map(input.streets)
        )
    }
}