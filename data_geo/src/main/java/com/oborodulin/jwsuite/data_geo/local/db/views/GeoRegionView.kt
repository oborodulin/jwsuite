package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity

@DatabaseView(
    viewName = GeoRegionView.VIEW_NAME,
    value = """
    SELECT r.regionId, ifnull(rtl.regionTlCode, r.regionCode) AS regionCode, 
            rtl.regionTlId, rtl.regionLocCode, rtl.regionTlCode, rtl.regionName, rtl.regionsId 
    FROM ${GeoRegionEntity.TABLE_NAME} r JOIN ${GeoRegionTlEntity.TABLE_NAME} rtl ON rtl.regionsId = r.regionId
    """
)
class GeoRegionView(
    @Embedded val data: GeoRegionEntity,
    @Embedded val tl: GeoRegionTlEntity
) {
    companion object {
        const val VIEW_NAME = "geo_regions_view"
    }
}