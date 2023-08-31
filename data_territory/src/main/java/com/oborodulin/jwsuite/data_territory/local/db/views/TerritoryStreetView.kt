package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView

@DatabaseView(
    viewName = TerritoryStreetView.VIEW_NAME,
    value = """
SELECT s.*, ts.* 
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
