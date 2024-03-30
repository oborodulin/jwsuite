package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity

@DatabaseView(
    viewName = RegionDistrictView.VIEW_NAME,
    value = """
    SELECT rd.regionDistrictId, ifnull(rdtl.regDistrictTlShortName, rd.regDistrictShortName) AS regDistrictShortName, rd.regDistrictType, 
            rd.regDistrictGeocode,
            rd.regDistrictOsmId, rd.${GeoRegionDistrictEntity.PREFIX}latitude, rd.${GeoRegionDistrictEntity.PREFIX}longitude, rd.rRegionsId, 
            rdtl.regionDistrictTlId, rdtl.regDistrictLocCode, rdtl.regDistrictTlShortName, rdtl.regDistrictName, rdtl.regionDistrictsId 
    FROM ${GeoRegionDistrictEntity.TABLE_NAME} rd JOIN ${GeoRegionDistrictTlEntity.TABLE_NAME} rdtl ON rdtl.regionDistrictsId = rd.regionDistrictId        
    """
)
class RegionDistrictView(
    @Embedded val data: GeoRegionDistrictEntity,
    @Embedded val tl: GeoRegionDistrictTlEntity
) {
    companion object {
        const val VIEW_NAME = "region_districts_view"
    }
}