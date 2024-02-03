package com.oborodulin.jwsuite.data_territory.local.db.mappers.territorystreet

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetViewToGeoStreetMapper
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet

class TerritoryStreetViewToTerritoryStreetMapper(private val ctx: Context, private val mapper: GeoStreetViewToGeoStreetMapper) :
    Mapper<TerritoryStreetView, TerritoryStreet>,
    NullableMapper<TerritoryStreetView, TerritoryStreet> {
    override fun map(input: TerritoryStreetView): TerritoryStreet {
        val territoryStreet = TerritoryStreet(
            ctx = ctx,
            territoryId = input.territoryStreet.tsTerritoriesId,
            street = mapper.map(input.street),
            isEvenSide = input.territoryStreet.isEvenSide,
            isPrivateSector = input.territoryStreet.isTerStreetPrivateSector,
            estimatedHouses = input.territoryStreet.estTerStreetHouses
        )
        territoryStreet.id = input.territoryStreet.territoryStreetId
        return territoryStreet
    }

    override fun nullableMap(input: TerritoryStreetView?) = input?.let { map(it) }
}