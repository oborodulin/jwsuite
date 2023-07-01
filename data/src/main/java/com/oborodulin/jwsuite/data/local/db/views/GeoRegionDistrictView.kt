package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictTlEntity

@DatabaseView(
    viewName = GeoRegionDistrictView.VIEW_NAME,
    value = """
SELECT rv.*, rd.*, rdtl.* 
    FROM ${GeoRegionDistrictEntity.TABLE_NAME} rd JOIN ${GeoRegionDistrictTlEntity.TABLE_NAME} rdtl ON rdtl.regionDistrictsId = rd.regionDistrictId
        JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = rd.rRegionsId
"""
)
class GeoRegionDistrictView(
    @Embedded
    val region: GeoRegionView,
    @Embedded
    val data: GeoRegionDistrictEntity,
    @Embedded
    val tl: GeoRegionDistrictTlEntity
) {
    companion object {
        const val VIEW_NAME = "geo_region_districts_view"
    }
}