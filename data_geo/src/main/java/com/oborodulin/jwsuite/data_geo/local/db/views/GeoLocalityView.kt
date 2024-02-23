package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity

@DatabaseView(
    viewName = GeoLocalityView.VIEW_NAME,
    value = """
SELECT rv.regionId AS ${GeoRegionEntity.PX}regionId, rv.regionCode AS ${GeoRegionEntity.PX}regionCode, 
            rv.regionTlId AS ${GeoRegionEntity.PX}regionTlId, rv.regionLocCode AS ${GeoRegionEntity.PX}regionLocCode, rv.regionTlCode AS ${GeoRegionEntity.PX}regionTlCode, 
            rv.regionName AS ${GeoRegionEntity.PX}regionName, rv.regionsId AS ${GeoRegionEntity.PX}regionsId, 
        rdv.regionDistrictId AS ${GeoRegionDistrictEntity.PX}regionDistrictId, rdv.regDistrictShortName  AS ${GeoRegionDistrictEntity.PX}regDistrictShortName, 
            rdv.rRegionsId  AS ${GeoRegionDistrictEntity.PX}rRegionsId,
            rdv.regionDistrictTlId  AS ${GeoRegionDistrictEntity.PX}regionDistrictTlId, rdv.regDistrictLocCode  AS ${GeoRegionDistrictEntity.PX}regDistrictLocCode,
            rdv.regDistrictTlShortName AS ${GeoRegionDistrictEntity.PX}regDistrictTlShortName, 
            rdv.regDistrictName  AS ${GeoRegionDistrictEntity.PX}regDistrictName, rdv.regionDistrictsId  AS ${GeoRegionDistrictEntity.PX}regionDistrictsId, 
        lv.localityId AS ${GeoLocalityEntity.PX}localityId, lv.localityCode AS ${GeoLocalityEntity.PX}localityCode, lv.localityType AS ${GeoLocalityEntity.PX}localityType, 
            lv.lRegionDistrictsId AS ${GeoLocalityEntity.PX}lRegionDistrictsId, lv.lRegionsId AS ${GeoLocalityEntity.PX}lRegionsId,
            lv.localityTlId AS ${GeoLocalityEntity.PX}localityTlId, lv.localityLocCode AS ${GeoLocalityEntity.PX}localityLocCode, lv.localityShortName AS ${GeoLocalityEntity.PX}localityShortName, 
            lv.localityName AS ${GeoLocalityEntity.PX}localityName, lv.localitiesId AS ${GeoLocalityEntity.PX}localitiesId
    FROM ${LocalityView.VIEW_NAME} lv JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = lv.localityLocCode
        LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rdv.regDistrictLocCode = lv.localityLocCode
"""
)
class GeoLocalityView(
    @Embedded(prefix = GeoRegionEntity.PX) val region: GeoRegionView,
    @Embedded(prefix = GeoRegionDistrictEntity.PX) val district: RegionDistrictView?,
    @Embedded(prefix = GeoLocalityEntity.PX) val locality: LocalityView
) {
    companion object {
        const val VIEW_NAME = "geo_localities_view"
    }
}