package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity

@DatabaseView(
    viewName = RegionView.VIEW_NAME,
    value = """
    SELECT r.regionId, ifnull(rtl.regionTlCode, r.regionCode) AS regionCode, r.regionGeocode,
            r.regionOsmId, r.${GeoRegionEntity.PREFIX}latitude, r.${GeoRegionEntity.PREFIX}longitude,
            rtl.regionTlId, rtl.regionLocCode, rtl.regionTlCode, rtl.regionName, rtl.regionsId 
    FROM ${GeoRegionEntity.TABLE_NAME} r JOIN ${GeoRegionTlEntity.TABLE_NAME} rtl ON rtl.regionsId = r.regionId
    """
)
class RegionView(
    @Embedded val data: GeoRegionEntity,
    @Embedded val tl: GeoRegionTlEntity
) {
    companion object {
        const val VIEW_NAME = "regions_view"
    }
}