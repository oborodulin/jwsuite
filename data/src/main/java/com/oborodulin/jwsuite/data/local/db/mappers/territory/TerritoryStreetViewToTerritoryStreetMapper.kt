package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data.local.db.views.TerritoryStreetView
import com.oborodulin.jwsuite.domain.model.TerritoryStreet

class TerritoryStreetViewToTerritoryStreetMapper(private val streetMapper: GeoStreetViewToGeoStreetMapper) :
    Mapper<TerritoryStreetView, TerritoryStreet> {
    override fun map(input: TerritoryStreetView): TerritoryStreet {
        val street = streetMapper.map(input.street)
        val territoryStreet = TerritoryStreet(
            territoryId = input.territoriesId,
            street = street,
            isEven = input.isEven,
            isPrivateSector = input.isPrivateSector
        )
        territoryStreet.id = input.territoryStreetId
        return territoryStreet
    }
}