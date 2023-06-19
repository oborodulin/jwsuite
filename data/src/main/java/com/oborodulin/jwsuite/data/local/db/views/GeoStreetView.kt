package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetTlEntity

@DatabaseView(
    viewName = GeoStreetView.VIEW_NAME,
    value = """
SELECT s.*, stl.* FROM ${GeoStreetEntity.TABLE_NAME} s JOIN ${GeoStreetTlEntity.TABLE_NAME} stl ON stl.streetsId = s.streetId
ORDER BY stl.streetName
"""
)
class GeoStreetView(
    @Embedded
    val data: GeoStreetEntity,
    @Embedded
    val tl: GeoStreetTlEntity,
) {
    companion object {
        const val VIEW_NAME = "geo_streets_view"
    }
}