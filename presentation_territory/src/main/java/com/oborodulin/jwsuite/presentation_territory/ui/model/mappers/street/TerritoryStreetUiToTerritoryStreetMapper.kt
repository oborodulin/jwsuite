package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import com.oborodulin.jwsuite.presentation_territory.ui.modules.geo.model.mappers.street.StreetUiToStreetMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUi

class TerritoryStreetUiToTerritoryStreetMapper(private val mapper: StreetUiToStreetMapper) :
    Mapper<TerritoryStreetUi, TerritoryStreet> {
    override fun map(input: TerritoryStreetUi): TerritoryStreet {
        val territoryStreet = TerritoryStreet(
            territoryId = input.territoryId,
            street = mapper.map(input.street),
            isEvenSide = input.isEvenSide,
            isPrivateSector = input.isPrivateSector,
            estimatedHouses = input.estimatedHouses
        )
        territoryStreet.id = input.id
        return territoryStreet
    }
}