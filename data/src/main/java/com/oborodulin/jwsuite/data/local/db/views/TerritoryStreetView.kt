package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryStreetEntity

@DatabaseView(
    viewName = TerritoryStreetView.VIEW_NAME,
    value = """
SELECT s.*, ts.territoryStreetId, ts.tsStreetsId, ts.tsTerritoriesId, ts.isEvenSide, ts.isTerStreetPrivateSector, ts.estTerStreetHouses 
FROM ${TerritoryStreetEntity.TABLE_NAME} ts JOIN ${GeoStreetView.VIEW_NAME} s ON s.streetId = ts.tsStreetsId
"""
)
class TerritoryStreetView(
    @Embedded val street: GeoStreetView,
    @Embedded val territoryStreet: TerritoryStreetEntity
) {
    companion object {
        const val VIEW_NAME = "territory_streets_view"
    }
}
