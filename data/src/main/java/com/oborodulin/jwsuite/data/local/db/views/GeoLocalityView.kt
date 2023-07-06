package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.data.util.Constants.PX_LOCALITY_REGION

@DatabaseView(
    viewName = GeoLocalityView.VIEW_NAME,
    value = """
SELECT rv.regionId AS ${PX_LOCALITY_REGION}_regionId, rv.regionCode AS ${PX_LOCALITY_REGION}_regionCode, 
            rv.regionTlId AS ${PX_LOCALITY_REGION}_regionTlId, rv.regionLocCode AS ${PX_LOCALITY_REGION}_regionLocCode, rv.regionName AS ${PX_LOCALITY_REGION}_regionName,
            rv.regionsId AS ${PX_LOCALITY_REGION}_regionsId, 
        rdv.*, l.*, ltl.*
    FROM ${GeoLocalityEntity.TABLE_NAME} l JOIN ${GeoLocalityTlEntity.TABLE_NAME} ltl ON ltl.localitiesId = l.localityId
        JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = l.lRegionsId
        LEFT JOIN ${GeoRegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = l.lRegionDistrictsId
"""
)
class GeoLocalityView(
    @Embedded(prefix = PX_LOCALITY_REGION) val region: GeoRegionView,
    @Embedded val regionDistrict: GeoRegionDistrictView?,
    @Embedded val data: GeoLocalityEntity,
    @Embedded val tl: GeoLocalityTlEntity
) {
    companion object {
        const val VIEW_NAME = "geo_localities_view"
    }
}