package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetUiToStreetMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUi

class TerritoryStreetUiToTerritoryStreetMapper(private val mapper: StreetUiToStreetMapper) :
    Mapper<TerritoryStreetUi, TerritoryStreet>, NullableMapper<TerritoryStreetUi, TerritoryStreet> {
    override fun map(input: TerritoryStreetUi): TerritoryStreet {
        val territoryStreet = TerritoryStreet(
            territoryId = input.territoryId,
            street = mapper.map(input.street),
            isEvenSide = input.isEvenSide,
            isPrivateSector = input.isPrivateSector,
            estimatedHouses = input.estimatedHouses,
            isCreateEstHouses = input.isCreateEstHouses
        )
        territoryStreet.id = input.id
        return territoryStreet
    }

    override fun nullableMap(input: TerritoryStreetUi?) = input?.let { map(it) }
}