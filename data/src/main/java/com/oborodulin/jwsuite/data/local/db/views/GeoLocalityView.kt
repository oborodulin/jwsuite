package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityTlEntity

@DatabaseView(
    viewName = GeoLocalityView.VIEW_NAME,
    value = """
SELECT rv.*, rdv.*, l.*, ltl.*
    FROM ${GeoLocalityEntity.TABLE_NAME} l JOIN ${GeoLocalityTlEntity.TABLE_NAME} ltl ON ltl.localitiesId = l.localityId
        JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = l.lRegionsId
        LEFT JOIN ${GeoRegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = l.lRegionDistrictsId
"""
)
class GeoLocalityView(
    @Embedded
    val region: GeoRegionView,
    @Embedded
    val regionDistrict: GeoRegionDistrictView?,
    @Embedded
    val data: GeoLocalityEntity,
    @Embedded
    val tl: GeoLocalityTlEntity
) {
    companion object {
        const val VIEW_NAME = "geo_localities_view"
    }
}