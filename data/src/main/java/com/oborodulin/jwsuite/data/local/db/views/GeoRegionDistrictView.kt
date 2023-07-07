package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.util.Constants.PX_DISTRICT_REGION
import com.oborodulin.jwsuite.data.util.Constants.PX_REGION_DISTRICT

@DatabaseView(
    viewName = GeoRegionDistrictView.VIEW_NAME,
    value = """
SELECT rv.regionId AS ${PX_DISTRICT_REGION}regionId, rv.regionCode AS ${PX_DISTRICT_REGION}regionCode, 
            rv.regionTlId AS ${PX_DISTRICT_REGION}regionTlId, rv.regionLocCode AS ${PX_DISTRICT_REGION}regionLocCode, rv.regionName AS ${PX_DISTRICT_REGION}regionName,
            rv.regionsId AS ${PX_DISTRICT_REGION}regionsId, 
        rdv.regionDistrictId AS ${PX_REGION_DISTRICT}regionDistrictId, rdv.regDistrictShortName  AS ${PX_REGION_DISTRICT}regDistrictShortName, 
            rdv.rRegionsId  AS ${PX_REGION_DISTRICT}rRegionsId,
            rdv.regionDistrictTlId  AS ${PX_REGION_DISTRICT}regionDistrictTlId, rdv.regDistrictLocCode  AS ${PX_REGION_DISTRICT}regDistrictLocCode, 
            rdv.regDistrictName  AS ${PX_REGION_DISTRICT}regDistrictName, rdv.regionDistrictsId  AS ${PX_REGION_DISTRICT}regionDistrictsId
    FROM ${RegionDistrictView.VIEW_NAME} rdv JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = rdv.rRegionsId AND rv.regionLocCode = rdv.regDistrictLocCode 
"""
)
class GeoRegionDistrictView(
    @Embedded(prefix = PX_DISTRICT_REGION) val region: GeoRegionView,
    @Embedded(prefix = PX_REGION_DISTRICT) val district: RegionDistrictView
) {
    companion object {
        const val VIEW_NAME = "geo_region_districts_view"
    }
}