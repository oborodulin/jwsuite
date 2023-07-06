package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.data.util.Constants.PX_DISTRICT_REGION

@DatabaseView(
    viewName = GeoRegionDistrictView.VIEW_NAME,
    value = """
SELECT rv.regionId AS ${PX_DISTRICT_REGION}_regionId, rv.regionCode AS ${PX_DISTRICT_REGION}_regionCode, 
            rv.regionTlId AS ${PX_DISTRICT_REGION}_regionTlId, rv.regionLocCode AS ${PX_DISTRICT_REGION}_regionLocCode, rv.regionName AS ${PX_DISTRICT_REGION}_regionName,
            rv.regionsId AS ${PX_DISTRICT_REGION}_regionsId, 
        rdv.* 
    FROM ${RegionDistrictView.VIEW_NAME} rdv JOIN ${GeoRegionView.VIEW_NAME} rv ON rdv.rRegionsId = rv.regionId
"""
)
class GeoRegionDistrictView(
    @Embedded(prefix = PX_DISTRICT_REGION) val region: GeoRegionView,
    @Embedded val district: RegionDistrictView
) {
    companion object {
        const val VIEW_NAME = "geo_region_districts_view"
    }
}