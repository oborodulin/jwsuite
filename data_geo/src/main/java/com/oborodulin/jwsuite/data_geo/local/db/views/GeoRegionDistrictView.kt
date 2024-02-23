package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity

@DatabaseView(
    viewName = GeoRegionDistrictView.VIEW_NAME,
    value = """
SELECT rv.regionId AS ${GeoRegionEntity.PX_DISTRICT}regionId, rv.regionCode AS ${GeoRegionEntity.PX_DISTRICT}regionCode, 
            rv.regionTlId AS ${GeoRegionEntity.PX_DISTRICT}regionTlId, rv.regionLocCode AS ${GeoRegionEntity.PX_DISTRICT}regionLocCode, rv.regionTlCode AS ${GeoRegionEntity.PX_DISTRICT}regionTlCode,  
            rv.regionName AS ${GeoRegionEntity.PX_DISTRICT}regionName, rv.regionsId AS ${GeoRegionEntity.PX_DISTRICT}regionsId, 
        rdv.regionDistrictId AS ${GeoRegionDistrictEntity.PX}regionDistrictId, rdv.regDistrictShortName  AS ${GeoRegionDistrictEntity.PX}regDistrictShortName, 
            rdv.rRegionsId  AS ${GeoRegionDistrictEntity.PX}rRegionsId,
            rdv.regionDistrictTlId  AS ${GeoRegionDistrictEntity.PX}regionDistrictTlId, rdv.regDistrictLocCode  AS ${GeoRegionDistrictEntity.PX}regDistrictLocCode,
            rdv.regDistrictTlShortName AS ${GeoRegionDistrictEntity.PX}regDistrictTlShortName,
            rdv.regDistrictName  AS ${GeoRegionDistrictEntity.PX}regDistrictName, rdv.regionDistrictsId  AS ${GeoRegionDistrictEntity.PX}regionDistrictsId
    FROM ${RegionDistrictView.VIEW_NAME} rdv JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = rdv.rRegionsId AND rv.regionLocCode = rdv.regDistrictLocCode 
"""
)
class GeoRegionDistrictView(
    @Embedded(prefix = GeoRegionEntity.PX_DISTRICT) val region: GeoRegionView,
    @Embedded(prefix = GeoRegionDistrictEntity.PX) val district: RegionDistrictView
) {
    companion object {
        const val VIEW_NAME = "geo_region_districts_view"
    }
}