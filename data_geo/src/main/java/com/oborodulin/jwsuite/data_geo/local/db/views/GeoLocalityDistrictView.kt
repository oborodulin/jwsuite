package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.util.Constants
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY_DISTRICT
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY_DISTRICT_LOCALITY
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY_DISTRICT_REGION
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_LOCALITY_DISTRICT_REGION_DISTRICT

@DatabaseView(
    viewName = GeoLocalityDistrictView.VIEW_NAME,
    value = """
SELECT rv.regionId AS ${PX_LOCALITY_DISTRICT_REGION}regionId, rv.regionCode AS ${PX_LOCALITY_DISTRICT_REGION}regionCode, 
            rv.regionTlId AS ${PX_LOCALITY_DISTRICT_REGION}regionTlId, rv.regionLocCode AS ${PX_LOCALITY_DISTRICT_REGION}regionLocCode, rv.regionTlCode AS ${PX_LOCALITY_DISTRICT_REGION}regionTlCode, 
            rv.regionName AS ${PX_LOCALITY_DISTRICT_REGION}regionName, rv.regionsId AS ${PX_LOCALITY_DISTRICT_REGION}regionsId, 
        rdv.regionDistrictId AS ${PX_LOCALITY_DISTRICT_REGION_DISTRICT}regionDistrictId, rdv.regDistrictShortName  AS ${PX_LOCALITY_DISTRICT_REGION_DISTRICT}regDistrictShortName, 
            rdv.rRegionsId  AS ${PX_LOCALITY_DISTRICT_REGION_DISTRICT}rRegionsId,
            rdv.regionDistrictTlId  AS ${PX_LOCALITY_DISTRICT_REGION_DISTRICT}regionDistrictTlId, rdv.regDistrictLocCode  AS ${PX_LOCALITY_DISTRICT_REGION_DISTRICT}regDistrictLocCode,
            rdv.regDistrictTlShortName AS ${PX_LOCALITY_DISTRICT_REGION_DISTRICT}regDistrictTlShortName,
            rdv.regDistrictName  AS ${PX_LOCALITY_DISTRICT_REGION_DISTRICT}regDistrictName, rdv.regionDistrictsId  AS ${PX_LOCALITY_DISTRICT_REGION_DISTRICT}regionDistrictsId, 
        lv.localityId AS ${PX_LOCALITY_DISTRICT_LOCALITY}localityId, lv.localityCode AS ${PX_LOCALITY_DISTRICT_LOCALITY}localityCode, lv.localityType AS ${PX_LOCALITY_DISTRICT_LOCALITY}localityType, 
            lv.lRegionDistrictsId AS ${PX_LOCALITY_DISTRICT_LOCALITY}lRegionDistrictsId, lv.lRegionsId AS ${PX_LOCALITY_DISTRICT_LOCALITY}lRegionsId,
            lv.localityTlId AS ${PX_LOCALITY_DISTRICT_LOCALITY}localityTlId, lv.localityLocCode AS ${PX_LOCALITY_DISTRICT_LOCALITY}localityLocCode, lv.localityShortName AS ${PX_LOCALITY_DISTRICT_LOCALITY}localityShortName, 
            lv.localityName AS ${PX_LOCALITY_DISTRICT_LOCALITY}localityName, lv.localitiesId AS ${PX_LOCALITY_DISTRICT_LOCALITY}localitiesId, 
        ldv.localityDistrictId AS ${PX_LOCALITY_DISTRICT}localityDistrictId, ldv.locDistrictShortName AS ${PX_LOCALITY_DISTRICT}locDistrictShortName, 
            ldv.ldLocalitiesId AS ${PX_LOCALITY_DISTRICT}ldLocalitiesId, ldv.localityDistrictTlId AS ${PX_LOCALITY_DISTRICT}localityDistrictTlId, 
            ldv.locDistrictLocCode AS ${PX_LOCALITY_DISTRICT}locDistrictLocCode, ldv.locDistrictName AS ${PX_LOCALITY_DISTRICT}locDistrictName, 
            ldv.localityDistrictsId AS ${PX_LOCALITY_DISTRICT}localityDistrictsId
FROM ${LocalityDistrictView.VIEW_NAME} ldv JOIN ${LocalityView.VIEW_NAME} lv ON lv.localityId = ldv.ldLocalitiesId AND lv.localityLocCode = ldv.locDistrictLocCode 
    JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = ldv.locDistrictLocCode
    LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rdv.regDistrictLocCode = ldv.locDistrictLocCode
"""
)
class GeoLocalityDistrictView(
    @Embedded(prefix = PX_LOCALITY_DISTRICT_REGION) val region: GeoRegionView,
    @Embedded(prefix = PX_LOCALITY_DISTRICT_REGION_DISTRICT) val district: RegionDistrictView?,
    @Embedded(prefix = PX_LOCALITY_DISTRICT_LOCALITY) val locality: LocalityView,
    @Embedded(prefix = PX_LOCALITY_DISTRICT) val localityDistrict: LocalityDistrictView
) {
    companion object {
        const val VIEW_NAME = "geo_locality_districts_view"
    }
}