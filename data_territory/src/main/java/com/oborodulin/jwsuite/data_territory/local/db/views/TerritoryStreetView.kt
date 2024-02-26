package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.views.StreetView
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity

@DatabaseView(
    viewName = TerritoryStreetView.VIEW_NAME,
    value = "SELECT sv.*, ts.* FROM ${TerritoryStreetEntity.TABLE_NAME} ts JOIN ${StreetView.VIEW_NAME} sv ON sv.streetId = ts.tsStreetsId"
)
class TerritoryStreetView(
    @Embedded val street: StreetView,
    @Embedded val territoryStreet: TerritoryStreetEntity
) {
    companion object {
        const val VIEW_NAME = "territory_streets_view"
    }
}
