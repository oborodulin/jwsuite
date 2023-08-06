package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryStreetEntity

@DatabaseView(
    viewName = TerritoryStreetHouseView.VIEW_NAME,
    value = "SELECT tsv.*, h.* FROM ${TerritoryStreetView.VIEW_NAME} tsv JOIN ${HouseEntity.TABLE_NAME} h ON h.hStreetsId = tsv.tsStreetsId"
)
class TerritoryStreetHouseView(
    @Embedded val territoryStreet: TerritoryStreetView,
    @Embedded val house: HouseEntity
) {
    companion object {
        const val VIEW_NAME = "territory_street_houses_view"
    }
}
