package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictTlEntity

@DatabaseView(
    viewName = GeoLocalityDistrictView.VIEW_NAME,
    value = """
SELECT ld.*, ldtl.* FROM ${GeoLocalityDistrictEntity.TABLE_NAME} ld JOIN ${GeoLocalityDistrictTlEntity.TABLE_NAME} ldtl ON ldtl.localityDistrictsId = ld.localityDistrictId
ORDER BY ldtl.districtName
"""
)
class GeoLocalityDistrictView(
    @Embedded
    val data: GeoLocalityDistrictEntity,
    @Embedded
    val tl: GeoLocalityDistrictTlEntity,
) {
    companion object {
        const val VIEW_NAME = "geo_locality_districts_view"
    }
}