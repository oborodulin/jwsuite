package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityView
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionDistrictView

@DatabaseView(
    viewName = CongregationView.VIEW_NAME,
    value = """
SELECT c.*, 
        rv.regionId AS ${CongregationEntity.PX_REGION}regionId, rv.regionCode AS ${CongregationEntity.PX_REGION}regionCode, 
            rv.regionTlId AS ${CongregationEntity.PX_REGION}regionTlId, rv.regionLocCode AS ${CongregationEntity.PX_REGION}regionLocCode, rv.regionTlCode AS ${CongregationEntity.PX_REGION}regionTlCode, 
            rv.regionName AS ${CongregationEntity.PX_REGION}regionName, rv.regionsId AS ${CongregationEntity.PX_REGION}regionsId,
        rdv.regionDistrictId AS ${CongregationEntity.PX_REGION_DISTRICT}regionDistrictId, rdv.regDistrictShortName AS ${CongregationEntity.PX_REGION_DISTRICT}regDistrictShortName, 
            rdv.rRegionsId AS ${CongregationEntity.PX_REGION_DISTRICT}rRegionsId,
            rdv.regionDistrictTlId AS ${CongregationEntity.PX_REGION_DISTRICT}regionDistrictTlId, rdv.regDistrictLocCode AS ${CongregationEntity.PX_REGION_DISTRICT}regDistrictLocCode,
            rdv.regDistrictTlShortName AS ${CongregationEntity.PX_REGION_DISTRICT}regDistrictTlShortName, 
            rdv.regDistrictName AS ${CongregationEntity.PX_REGION_DISTRICT}regDistrictName, rdv.regionDistrictsId AS ${CongregationEntity.PX_REGION_DISTRICT}regionDistrictsId,
        lv.localityId AS ${CongregationEntity.PX_LOCALITY}localityId, lv.localityCode AS ${CongregationEntity.PX_LOCALITY}localityCode, lv.localityType AS ${CongregationEntity.PX_LOCALITY}localityType, 
            lv.lRegionDistrictsId AS ${CongregationEntity.PX_LOCALITY}lRegionDistrictsId, lv.lRegionsId AS ${CongregationEntity.PX_LOCALITY}lRegionsId,
            lv.localityTlId AS ${CongregationEntity.PX_LOCALITY}localityTlId, lv.localityLocCode AS ${CongregationEntity.PX_LOCALITY}localityLocCode, lv.localityShortName AS ${CongregationEntity.PX_LOCALITY}localityShortName, 
            lv.localityName AS ${CongregationEntity.PX_LOCALITY}localityName, lv.localitiesId AS ${CongregationEntity.PX_LOCALITY}localitiesId
FROM ${CongregationEntity.TABLE_NAME} c JOIN ${LocalityView.VIEW_NAME} lv ON lv.localityId = c.cLocalitiesId 
    JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = lv.localityLocCode
    LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rv.regionLocCode = lv.localityLocCode
"""
)
class CongregationView(
    @Embedded val congregation: CongregationEntity,
    @Embedded(prefix = CongregationEntity.PX_REGION) val region: GeoRegionView,
    @Embedded(prefix = CongregationEntity.PX_REGION_DISTRICT) val district: RegionDistrictView?,
    @Embedded(prefix = CongregationEntity.PX_LOCALITY) val locality: LocalityView
) {
    companion object {
        const val VIEW_NAME = "congregations_view"
    }
}