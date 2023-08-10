package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data.local.db.views.TerritoryStreetView
import com.oborodulin.jwsuite.domain.model.TerritoryStreet

class TerritoryStreetViewToTerritoryStreetMapper(private val streetMapper: GeoStreetViewToGeoStreetMapper) :
    Mapper<TerritoryStreetView, TerritoryStreet> {
    override fun map(input: TerritoryStreetView): TerritoryStreet {
        val territoryStreet = TerritoryStreet(
            territoryId = input.territoryStreet.tsTerritoriesId,
            street = streetMapper.map(input.street),
            isEven = input.territoryStreet.isEvenSide,
            isPrivateSector = input.territoryStreet.isTerStreetPrivateSector,
            estimatedHouses = input.territoryStreet.estTerStreetHouses
        )
        territoryStreet.id = input.territoryStreet.territoryStreetId
        return territoryStreet
    }
}