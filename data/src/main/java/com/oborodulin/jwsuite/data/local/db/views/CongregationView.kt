package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data.util.Constants.PX_CONGREGATION_LOCALITY
import com.oborodulin.jwsuite.data.util.Constants.PX_CONGREGATION_REGION
import com.oborodulin.jwsuite.data.util.Constants.PX_CONGREGATION_REGION_DISTRICT

@DatabaseView(
    viewName = CongregationView.VIEW_NAME,
    value = """
SELECT c.*, 
        rv.regionId AS ${PX_CONGREGATION_REGION}regionId, rv.regionCode AS ${PX_CONGREGATION_REGION}regionCode, 
            rv.regionTlId AS ${PX_CONGREGATION_REGION}regionTlId, rv.regionLocCode AS ${PX_CONGREGATION_REGION}regionLocCode, rv.regionName AS ${PX_CONGREGATION_REGION}regionName,
            rv.regionsId AS ${PX_CONGREGATION_REGION}regionsId,
        rdv.regionDistrictId AS ${PX_CONGREGATION_REGION_DISTRICT}regionDistrictId, rdv.regDistrictShortName  AS ${PX_CONGREGATION_REGION_DISTRICT}regDistrictShortName, 
            rdv.rRegionsId  AS ${PX_CONGREGATION_REGION_DISTRICT}rRegionsId,
            rdv.regionDistrictTlId  AS ${PX_CONGREGATION_REGION_DISTRICT}regionDistrictTlId, rdv.regDistrictLocCode  AS ${PX_CONGREGATION_REGION_DISTRICT}regDistrictLocCode, 
            rdv.regDistrictName  AS ${PX_CONGREGATION_REGION_DISTRICT}regDistrictName, rdv.regionDistrictsId  AS ${PX_CONGREGATION_REGION_DISTRICT}regionDistrictsId,
        lv.localityId AS ${PX_CONGREGATION_LOCALITY}localityId, lv.localityCode AS ${PX_CONGREGATION_LOCALITY}localityCode, lv.localityType AS ${PX_CONGREGATION_LOCALITY}localityType, 
            lv.lRegionDistrictsId AS ${PX_CONGREGATION_LOCALITY}lRegionDistrictsId, lv.lRegionsId AS ${PX_CONGREGATION_LOCALITY}lRegionsId,
            lv.localityTlId AS ${PX_CONGREGATION_LOCALITY}localityTlId, lv.localityLocCode AS ${PX_CONGREGATION_LOCALITY}localityLocCode, lv.localityShortName AS ${PX_CONGREGATION_LOCALITY}localityShortName, 
            lv.localityName AS ${PX_CONGREGATION_LOCALITY}localityName, lv.localitiesId AS ${PX_CONGREGATION_LOCALITY}localitiesId
FROM ${CongregationEntity.TABLE_NAME} c JOIN ${LocalityView.VIEW_NAME} lv ON lv.localityId = c.cLocalitiesId 
    JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = lv.localityLocCode
    LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rv.regionLocCode = lv.localityLocCode
ORDER BY c.congregationName
"""
)
class CongregationView(
    @Embedded val congregation: CongregationEntity,
    @Embedded(prefix = PX_CONGREGATION_REGION) val region: GeoRegionView,
    @Embedded(prefix = PX_CONGREGATION_REGION_DISTRICT) val district: RegionDistrictView?,
    @Embedded(prefix = PX_CONGREGATION_LOCALITY) val locality: LocalityView
) {
    companion object {
        const val VIEW_NAME = "congregations_view"
    }
}