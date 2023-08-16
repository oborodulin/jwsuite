package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY_REGION
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY_REGION_DISTRICT

@DatabaseView(
    viewName = GeoLocalityView.VIEW_NAME,
    value = """
SELECT rv.regionId AS ${PX_LOCALITY_REGION}regionId, rv.regionCode AS ${PX_LOCALITY_REGION}regionCode, 
            rv.regionTlId AS ${PX_LOCALITY_REGION}regionTlId, rv.regionLocCode AS ${PX_LOCALITY_REGION}regionLocCode, rv.regionName AS ${PX_LOCALITY_REGION}regionName,
            rv.regionsId AS ${PX_LOCALITY_REGION}regionsId, 
        rdv.regionDistrictId AS ${PX_LOCALITY_REGION_DISTRICT}regionDistrictId, rdv.regDistrictShortName  AS ${PX_LOCALITY_REGION_DISTRICT}regDistrictShortName, 
            rdv.rRegionsId  AS ${PX_LOCALITY_REGION_DISTRICT}rRegionsId,
            rdv.regionDistrictTlId  AS ${PX_LOCALITY_REGION_DISTRICT}regionDistrictTlId, rdv.regDistrictLocCode  AS ${PX_LOCALITY_REGION_DISTRICT}regDistrictLocCode, 
            rdv.regDistrictName  AS ${PX_LOCALITY_REGION_DISTRICT}regDistrictName, rdv.regionDistrictsId  AS ${PX_LOCALITY_REGION_DISTRICT}regionDistrictsId, 
        lv.localityId AS ${PX_LOCALITY}localityId, lv.localityCode AS ${PX_LOCALITY}localityCode, lv.localityType AS ${PX_LOCALITY}localityType, 
            lv.lRegionDistrictsId AS ${PX_LOCALITY}lRegionDistrictsId, lv.lRegionsId AS ${PX_LOCALITY}lRegionsId,
            lv.localityTlId AS ${PX_LOCALITY}localityTlId, lv.localityLocCode AS ${PX_LOCALITY}localityLocCode, lv.localityShortName AS ${PX_LOCALITY}localityShortName, 
            lv.localityName AS ${PX_LOCALITY}localityName, lv.localitiesId AS ${PX_LOCALITY}localitiesId
    FROM ${LocalityView.VIEW_NAME} lv JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = lv.localityLocCode
        LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rdv.regDistrictLocCode = lv.localityLocCode
"""
)
class GeoLocalityView(
    @Embedded(prefix = PX_LOCALITY_REGION) val region: GeoRegionView,
    @Embedded(prefix = PX_LOCALITY_REGION_DISTRICT) val district: RegionDistrictView?,
    @Embedded(prefix = PX_LOCALITY) val locality: LocalityView
) {
    companion object {
        const val VIEW_NAME = "geo_localities_view"
    }
}