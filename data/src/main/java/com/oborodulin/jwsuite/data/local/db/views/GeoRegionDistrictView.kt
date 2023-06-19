package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictTlEntity

@DatabaseView(
    viewName = GeoRegionDistrictView.VIEW_NAME,
    value = """
SELECT rd.*, rdtl.* FROM ${GeoRegionDistrictEntity.TABLE_NAME} rd JOIN ${GeoRegionDistrictTlEntity.TABLE_NAME} rdtl ON rdtl.regionDistrictsId = rd.regionDistrictId
ORDER BY rdtl.districtName
"""
)
class GeoRegionDistrictView(
    @Embedded
    val data: GeoRegionDistrictEntity,
    @Embedded
    val tl: GeoRegionDistrictTlEntity,
) {
    companion object {
        const val VIEW_NAME = "geo_region_districts_view"
    }
}