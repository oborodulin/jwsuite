package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetTlEntity

@DatabaseView(
    viewName = StreetView.VIEW_NAME,
    value = "SELECT s.*, stl.* FROM ${GeoStreetEntity.TABLE_NAME} s JOIN ${GeoStreetTlEntity.TABLE_NAME} stl ON stl.streetsId = s.streetId"
)
class StreetView(
    @Embedded val data: GeoStreetEntity,
    @Embedded val tl: GeoStreetTlEntity,
) {
    companion object {
        const val VIEW_NAME = "streets_view"
    }
}