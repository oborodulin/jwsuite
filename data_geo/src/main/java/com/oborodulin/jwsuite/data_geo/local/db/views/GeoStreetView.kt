package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity

@DatabaseView(
    viewName = GeoStreetView.VIEW_NAME,
    value = """
    SELECT lv.*, sv.* 
    FROM ${StreetView.VIEW_NAME} sv JOIN ${GeoLocalityView.VIEW_NAME} lv 
        ON lv.${GeoLocalityEntity.PX}localityId = sv.sLocalitiesId AND lv.${GeoLocalityEntity.PX}localityLocCode = sv.streetLocCode
"""
)
class GeoStreetView(
    @Embedded val locality: GeoLocalityView,
    @Embedded val street: StreetView
) {
    companion object {
        const val VIEW_NAME = "geo_streets_view"
    }
}