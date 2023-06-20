package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryStreetEntity
import java.util.UUID

@DatabaseView(
    viewName = TerritoryStreetView.VIEW_NAME,
    value = """
SELECT s.*, ts.territoryStreetId, ts.territoriesId, ts.isEven, ts.isPrivateSector, ts.estimatedHouses 
FROM ${TerritoryStreetEntity.TABLE_NAME} ts JOIN ${GeoStreetView.VIEW_NAME} s ON s.streetId = ts.streetsId
"""
)
class TerritoryStreetView(
    @Embedded
    val street: GeoStreetView,
    val territoryStreetId: UUID,
    val territoriesId: UUID,
    val isEven: Boolean? = null,
    val isPrivateSector: Boolean? = null,
    val estimatedHouses: Int? = null
) {
    companion object {
        const val VIEW_NAME = "territory_streets_view"
    }
}
