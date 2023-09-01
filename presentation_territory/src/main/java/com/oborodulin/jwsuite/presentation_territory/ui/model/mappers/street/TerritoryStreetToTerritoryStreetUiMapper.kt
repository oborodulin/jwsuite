package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetToStreetUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUi

class TerritoryStreetToTerritoryStreetUiMapper(private val mapper: StreetToStreetUiMapper) :
    Mapper<TerritoryStreet, TerritoryStreetUi>, NullableMapper<TerritoryStreet, TerritoryStreetUi> {
    override fun map(input: TerritoryStreet): TerritoryStreetUi {
        val territoryStreetUi = TerritoryStreetUi(
            territoryId = input.territoryId,
            street = mapper.map(input.street),
            isEvenSide = input.isEvenSide,
            isPrivateSector = input.isPrivateSector,
            estimatedHouses = input.estimatedHouses
        )
        territoryStreetUi.id = input.id
        return territoryStreetUi
    }

    override fun nullableMap(input: TerritoryStreet?) = input?.let { map(it) }
}