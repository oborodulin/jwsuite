package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_MICRODISTRICT_LOCALITY
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_MICRODISTRICT_LOCALITY_DISTRICT
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_MICRODISTRICT_REGION
import com.oborodulin.jwsuite.data_geo.util.Constants.PX_MICRODISTRICT_REGION_DISTRICT

@DatabaseView(
    viewName = GeoMicrodistrictView.VIEW_NAME,
    value = """
SELECT rv.regionId AS ${PX_MICRODISTRICT_REGION}regionId, rv.regionCode AS ${PX_MICRODISTRICT_REGION}regionCode, 
            rv.regionTlId AS ${PX_MICRODISTRICT_REGION}regionTlId, rv.regionLocCode AS ${PX_MICRODISTRICT_REGION}regionLocCode, rv.regionName AS ${PX_MICRODISTRICT_REGION}regionName,
            rv.regionsId AS ${PX_MICRODISTRICT_REGION}regionsId, 
        rdv.regionDistrictId AS ${PX_MICRODISTRICT_REGION_DISTRICT}regionDistrictId, rdv.regDistrictShortName  AS ${PX_MICRODISTRICT_REGION_DISTRICT}regDistrictShortName, 
            rdv.rRegionsId AS ${PX_MICRODISTRICT_REGION_DISTRICT}rRegionsId,
            rdv.regionDistrictTlId AS ${PX_MICRODISTRICT_REGION_DISTRICT}regionDistrictTlId, rdv.regDistrictLocCode  AS ${PX_MICRODISTRICT_REGION_DISTRICT}regDistrictLocCode, 
            rdv.regDistrictName AS ${PX_MICRODISTRICT_REGION_DISTRICT}regDistrictName, rdv.regionDistrictsId  AS ${PX_MICRODISTRICT_REGION_DISTRICT}regionDistrictsId, 
        lv.localityId AS ${PX_MICRODISTRICT_LOCALITY}localityId, lv.localityCode AS ${PX_MICRODISTRICT_LOCALITY}localityCode, lv.localityType AS ${PX_MICRODISTRICT_LOCALITY}localityType, 
            lv.lRegionDistrictsId AS ${PX_MICRODISTRICT_LOCALITY}lRegionDistrictsId, lv.lRegionsId AS ${PX_MICRODISTRICT_LOCALITY}lRegionsId,
            lv.localityTlId AS ${PX_MICRODISTRICT_LOCALITY}localityTlId, lv.localityLocCode AS ${PX_MICRODISTRICT_LOCALITY}localityLocCode, lv.localityShortName AS ${PX_MICRODISTRICT_LOCALITY}localityShortName, 
            lv.localityName AS ${PX_MICRODISTRICT_LOCALITY}localityName, lv.localitiesId AS ${PX_MICRODISTRICT_LOCALITY}localitiesId, 
        ldv.localityDistrictId AS ${PX_MICRODISTRICT_LOCALITY_DISTRICT}localityDistrictId, ldv.locDistrictShortName AS ${PX_MICRODISTRICT_LOCALITY_DISTRICT}locDistrictShortName, 
            ldv.ldLocalitiesId AS ${PX_MICRODISTRICT_LOCALITY_DISTRICT}ldLocalitiesId, ldv.localityDistrictTlId AS ${PX_MICRODISTRICT_LOCALITY_DISTRICT}localityDistrictTlId, 
            ldv.locDistrictLocCode AS ${PX_MICRODISTRICT_LOCALITY_DISTRICT}locDistrictLocCode, ldv.locDistrictName AS ${PX_MICRODISTRICT_LOCALITY_DISTRICT}locDistrictName, 
            ldv.localityDistrictsId AS ${PX_MICRODISTRICT_LOCALITY_DISTRICT}localityDistrictsId, 
        mdv.* 
FROM ${MicrodistrictView.VIEW_NAME} mdv JOIN ${LocalityDistrictView.VIEW_NAME} ldv ON ldv.localityDistrictId = mdv.mLocalityDistrictsId AND ldv.locDistrictLocCode = mdv.microdistrictLocCode 
    JOIN ${LocalityView.VIEW_NAME} lv ON lv.localityId = ldv.ldLocalitiesId AND lv.localityLocCode = mdv.microdistrictLocCode
    JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = mdv.microdistrictLocCode
    LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rdv.regDistrictLocCode = mdv.microdistrictLocCode
"""
)
class GeoMicrodistrictView(
    @Embedded(prefix = PX_MICRODISTRICT_REGION) val region: GeoRegionView,
    @Embedded(prefix = PX_MICRODISTRICT_REGION_DISTRICT) val district: RegionDistrictView?,
    @Embedded(prefix = PX_MICRODISTRICT_LOCALITY) val locality: LocalityView,
    @Embedded(prefix = PX_MICRODISTRICT_LOCALITY_DISTRICT) val localityDistrict: LocalityDistrictView,
    @Embedded val microdistrict: MicrodistrictView
) {
    companion object {
        const val VIEW_NAME = "geo_microdistricts_view"
    }
}