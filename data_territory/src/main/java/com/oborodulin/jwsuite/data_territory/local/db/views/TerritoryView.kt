package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationView
import com.oborodulin.jwsuite.data_congregation.util.Constants.PX_CONGREGATION_LOCALITY
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityDistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityView
import com.oborodulin.jwsuite.data_geo.local.db.views.MicrodistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionDistrictView
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_LD_LOCALITY
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_LD_LOCALITY_DISTRICT
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_LD_REGION
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_LD_REGION_DISTRICT
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_LOCALITY
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_MICRODISTRICT
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_M_LOCALITY
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_M_LOCALITY_DISTRICT
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_M_REGION
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_M_REGION_DISTRICT
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_REGION
import com.oborodulin.jwsuite.data_territory.util.Constants.PX_TERRITORY_REGION_DISTRICT
import com.oborodulin.jwsuite.domain.util.Constants.PRM_TERRITORY_BUSINESS_MARK_VAL

@DatabaseView(
    viewName = TerritoryView.VIEW_NAME,
    value = """
SELECT t.*, cv.*, tc.*,
        rv.regionId AS ${PX_TERRITORY_REGION}regionId, rv.regionCode AS ${PX_TERRITORY_REGION}regionCode, 
            rv.regionTlId AS ${PX_TERRITORY_REGION}regionTlId, rv.regionLocCode AS ${PX_TERRITORY_REGION}regionLocCode, rv.regionTlCode AS ${PX_TERRITORY_REGION}regionTlCode,
            rv.regionName AS ${PX_TERRITORY_REGION}regionName, rv.regionsId AS ${PX_TERRITORY_REGION}regionsId, 
        rdv.regionDistrictId AS ${PX_TERRITORY_REGION_DISTRICT}regionDistrictId, rdv.regDistrictShortName AS ${PX_TERRITORY_REGION_DISTRICT}regDistrictShortName, 
            rdv.rRegionsId AS ${PX_TERRITORY_REGION_DISTRICT}rRegionsId,
            rdv.regionDistrictTlId AS ${PX_TERRITORY_REGION_DISTRICT}regionDistrictTlId, rdv.regDistrictLocCode AS ${PX_TERRITORY_REGION_DISTRICT}regDistrictLocCode,
            rdv.regDistrictTlShortName AS ${PX_TERRITORY_REGION_DISTRICT}regDistrictTlShortName, 
            rdv.regDistrictName AS ${PX_TERRITORY_REGION_DISTRICT}regDistrictName, rdv.regionDistrictsId AS ${PX_TERRITORY_REGION_DISTRICT}regionDistrictsId, 
        lv.localityId AS ${PX_TERRITORY_LOCALITY}localityId, lv.localityCode AS ${PX_TERRITORY_LOCALITY}localityCode, lv.localityType AS ${PX_TERRITORY_LOCALITY}localityType, 
            lv.lRegionDistrictsId AS ${PX_TERRITORY_LOCALITY}lRegionDistrictsId, lv.lRegionsId AS ${PX_TERRITORY_LOCALITY}lRegionsId,
            lv.localityTlId AS ${PX_TERRITORY_LOCALITY}localityTlId, lv.localityLocCode AS ${PX_TERRITORY_LOCALITY}localityLocCode, lv.localityShortName AS ${PX_TERRITORY_LOCALITY}localityShortName, 
            lv.localityName AS ${PX_TERRITORY_LOCALITY}localityName, lv.localitiesId AS ${PX_TERRITORY_LOCALITY}localitiesId,
            
        rvl.regionId AS ${PX_TERRITORY_LD_REGION}regionId, rvl.regionCode AS ${PX_TERRITORY_LD_REGION}regionCode, 
            rvl.regionTlId AS ${PX_TERRITORY_LD_REGION}regionTlId, rvl.regionLocCode AS ${PX_TERRITORY_LD_REGION}regionLocCode, rvl.regionTlCode AS ${PX_TERRITORY_LD_REGION}regionTlCode,
            rvl.regionName AS ${PX_TERRITORY_LD_REGION}regionName, rvl.regionsId AS ${PX_TERRITORY_LD_REGION}regionsId, 
        rdvl.regionDistrictId AS ${PX_TERRITORY_LD_REGION_DISTRICT}regionDistrictId, rdvl.regDistrictShortName AS ${PX_TERRITORY_LD_REGION_DISTRICT}regDistrictShortName, 
            rdvl.rRegionsId AS ${PX_TERRITORY_LD_REGION_DISTRICT}rRegionsId,
            rdvl.regionDistrictTlId AS ${PX_TERRITORY_LD_REGION_DISTRICT}regionDistrictTlId, rdvl.regDistrictLocCode AS ${PX_TERRITORY_LD_REGION_DISTRICT}regDistrictLocCode,
            rdvl.regDistrictTlShortName AS ${PX_TERRITORY_LD_REGION_DISTRICT}regDistrictTlShortName,
            rdvl.regDistrictName AS ${PX_TERRITORY_LD_REGION_DISTRICT}regDistrictName, rdvl.regionDistrictsId AS ${PX_TERRITORY_LD_REGION_DISTRICT}regionDistrictsId, 
        lvl.localityId AS ${PX_TERRITORY_LD_LOCALITY}localityId, lvl.localityCode AS ${PX_TERRITORY_LD_LOCALITY}localityCode, lvl.localityType AS ${PX_TERRITORY_LD_LOCALITY}localityType, 
            lvl.lRegionDistrictsId AS ${PX_TERRITORY_LD_LOCALITY}lRegionDistrictsId, lvl.lRegionsId AS ${PX_TERRITORY_LD_LOCALITY}lRegionsId,
            lvl.localityTlId AS ${PX_TERRITORY_LD_LOCALITY}localityTlId, lvl.localityLocCode AS ${PX_TERRITORY_LD_LOCALITY}localityLocCode, lvl.localityShortName AS ${PX_TERRITORY_LD_LOCALITY}localityShortName, 
            lvl.localityName AS ${PX_TERRITORY_LD_LOCALITY}localityName, lvl.localitiesId AS ${PX_TERRITORY_LD_LOCALITY}localitiesId, 
        ldvl.localityDistrictId AS ${PX_TERRITORY_LD_LOCALITY_DISTRICT}localityDistrictId, ldvl.locDistrictShortName AS ${PX_TERRITORY_LD_LOCALITY_DISTRICT}locDistrictShortName, 
            ldvl.ldLocalitiesId AS ${PX_TERRITORY_LD_LOCALITY_DISTRICT}ldLocalitiesId, ldvl.localityDistrictTlId AS ${PX_TERRITORY_LD_LOCALITY_DISTRICT}localityDistrictTlId, 
            ldvl.locDistrictLocCode AS ${PX_TERRITORY_LD_LOCALITY_DISTRICT}locDistrictLocCode, ldvl.locDistrictName AS ${PX_TERRITORY_LD_LOCALITY_DISTRICT}locDistrictName, 
            ldvl.localityDistrictsId AS ${PX_TERRITORY_LD_LOCALITY_DISTRICT}localityDistrictsId, 

        rvm.regionId AS ${PX_TERRITORY_M_REGION}regionId, rvm.regionCode AS ${PX_TERRITORY_M_REGION}regionCode, 
            rvm.regionTlId AS ${PX_TERRITORY_M_REGION}regionTlId, rvm.regionLocCode AS ${PX_TERRITORY_M_REGION}regionLocCode, rvm.regionTlCode AS ${PX_TERRITORY_M_REGION}regionTlCode,
            rvm.regionName AS ${PX_TERRITORY_M_REGION}regionName, rvm.regionsId AS ${PX_TERRITORY_M_REGION}regionsId, 
        rdvm.regionDistrictId AS ${PX_TERRITORY_M_REGION_DISTRICT}regionDistrictId, rdvm.regDistrictShortName AS ${PX_TERRITORY_M_REGION_DISTRICT}regDistrictShortName, 
            rdvm.rRegionsId AS ${PX_TERRITORY_M_REGION_DISTRICT}rRegionsId,
            rdvm.regionDistrictTlId AS ${PX_TERRITORY_M_REGION_DISTRICT}regionDistrictTlId, rdvm.regDistrictLocCode AS ${PX_TERRITORY_M_REGION_DISTRICT}regDistrictLocCode,
            rdvm.regDistrictTlShortName AS ${PX_TERRITORY_M_REGION_DISTRICT}regDistrictTlShortName, 
            rdvm.regDistrictName AS ${PX_TERRITORY_M_REGION_DISTRICT}regDistrictName, rdvm.regionDistrictsId AS ${PX_TERRITORY_M_REGION_DISTRICT}regionDistrictsId, 
        lvm.localityId AS ${PX_TERRITORY_M_LOCALITY}localityId, lvm.localityCode AS ${PX_TERRITORY_M_LOCALITY}localityCode, lvm.localityType AS ${PX_TERRITORY_M_LOCALITY}localityType, 
            lvm.lRegionDistrictsId AS ${PX_TERRITORY_M_LOCALITY}lRegionDistrictsId, lvm.lRegionsId AS ${PX_TERRITORY_M_LOCALITY}lRegionsId,
            lvm.localityTlId AS ${PX_TERRITORY_M_LOCALITY}localityTlId, lvm.localityLocCode AS ${PX_TERRITORY_M_LOCALITY}localityLocCode, lvm.localityShortName AS ${PX_TERRITORY_M_LOCALITY}localityShortName, 
            lvm.localityName AS ${PX_TERRITORY_M_LOCALITY}localityName, lvm.localitiesId AS ${PX_TERRITORY_M_LOCALITY}localitiesId, 
        ldvm.localityDistrictId AS ${PX_TERRITORY_M_LOCALITY_DISTRICT}localityDistrictId, ldvm.locDistrictShortName AS ${PX_TERRITORY_M_LOCALITY_DISTRICT}locDistrictShortName, 
            ldvm.ldLocalitiesId AS ${PX_TERRITORY_M_LOCALITY_DISTRICT}ldLocalitiesId, ldvm.localityDistrictTlId AS ${PX_TERRITORY_M_LOCALITY_DISTRICT}localityDistrictTlId, 
            ldvm.locDistrictLocCode AS ${PX_TERRITORY_M_LOCALITY_DISTRICT}locDistrictLocCode, ldvm.locDistrictName AS ${PX_TERRITORY_M_LOCALITY_DISTRICT}locDistrictName, 
            ldvm.localityDistrictsId AS ${PX_TERRITORY_M_LOCALITY_DISTRICT}localityDistrictsId, 
        mdv.microdistrictId AS ${PX_TERRITORY_MICRODISTRICT}microdistrictId, mdv.microdistrictType AS ${PX_TERRITORY_MICRODISTRICT}microdistrictType, 
            mdv.microdistrictShortName AS ${PX_TERRITORY_MICRODISTRICT}microdistrictShortName, mdv.mLocalityDistrictsId AS ${PX_TERRITORY_MICRODISTRICT}mLocalityDistrictsId, 
            mdv.mLocalitiesId AS ${PX_TERRITORY_MICRODISTRICT}mLocalitiesId, mdv.microdistrictTlId AS ${PX_TERRITORY_MICRODISTRICT}microdistrictTlId, 
            mdv.microdistrictLocCode AS ${PX_TERRITORY_MICRODISTRICT}microdistrictLocCode, mdv.microdistrictName AS ${PX_TERRITORY_MICRODISTRICT}microdistrictName, 
            mdv.microdistrictsId AS ${PX_TERRITORY_MICRODISTRICT}microdistrictsId,
        s.paramValue AS territoryBusinessMark
FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${CongregationView.VIEW_NAME} cv ON cv.congregationId = t.tCongregationsId
    JOIN ${TerritoryCategoryEntity.TABLE_NAME} tc ON tc.territoryCategoryId = t.tTerritoryCategoriesId

    JOIN ${LocalityView.VIEW_NAME} lv ON lv.localityId = t.tLocalitiesId AND lv.localityLocCode = cv.${PX_CONGREGATION_LOCALITY}localityLocCode
        JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = lv.localityLocCode
        LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rdv.regDistrictLocCode = lv.localityLocCode
        
    LEFT JOIN ${LocalityDistrictView.VIEW_NAME} ldvl ON ldvl.localityDistrictId = t.tLocalityDistrictsId AND ldvl.locDistrictLocCode = cv.${PX_CONGREGATION_LOCALITY}localityLocCode
        LEFT JOIN ${LocalityView.VIEW_NAME} lvl ON lvl.localityId = ldvl.ldLocalitiesId AND lvl.localityLocCode = cv.${PX_CONGREGATION_LOCALITY}localityLocCode
        LEFT JOIN ${GeoRegionView.VIEW_NAME} rvl ON rvl.regionId = lvl.lRegionsId AND rvl.regionLocCode = cv.${PX_CONGREGATION_LOCALITY}localityLocCode
        LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdvl ON rdvl.regionDistrictId = lvl.lRegionDistrictsId AND rdvl.regDistrictLocCode = cv.${PX_CONGREGATION_LOCALITY}localityLocCode
    
    LEFT JOIN ${MicrodistrictView.VIEW_NAME} mdv ON mdv.microdistrictId = t.tMicrodistrictsId AND mdv.microdistrictLocCode = cv.${PX_CONGREGATION_LOCALITY}localityLocCode
        LEFT JOIN ${LocalityDistrictView.VIEW_NAME} ldvm ON ldvm.localityDistrictId = mdv.mLocalityDistrictsId AND ldvm.locDistrictLocCode = cv.${PX_CONGREGATION_LOCALITY}localityLocCode
        LEFT JOIN ${LocalityView.VIEW_NAME} lvm ON lvm.localityId = ldvm.ldLocalitiesId AND lvm.localityLocCode = cv.${PX_CONGREGATION_LOCALITY}localityLocCode
        LEFT JOIN ${GeoRegionView.VIEW_NAME} rvm ON rvm.regionId = lvm.lRegionsId AND rvm.regionLocCode = cv.${PX_CONGREGATION_LOCALITY}localityLocCode
        LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdvm ON rdvm.regionDistrictId = lvm.lRegionDistrictsId AND rdvm.regDistrictLocCode = cv.${PX_CONGREGATION_LOCALITY}localityLocCode

    JOIN ${AppSettingEntity.TABLE_NAME} s ON s.paramName = $PRM_TERRITORY_BUSINESS_MARK_VAL
"""
)
class TerritoryView(
    @Embedded val territory: TerritoryEntity,
    @Embedded val congregation: CongregationView,
    @Embedded val territoryCategory: TerritoryCategoryEntity,

    @Embedded(prefix = PX_TERRITORY_REGION) val tRegion: GeoRegionView,
    @Embedded(prefix = PX_TERRITORY_REGION_DISTRICT) val tDistrict: RegionDistrictView?,
    @Embedded(prefix = PX_TERRITORY_LOCALITY) val tLocality: LocalityView,

    @Embedded(prefix = PX_TERRITORY_LD_REGION) val tldRegion: GeoRegionView?,
    @Embedded(prefix = PX_TERRITORY_LD_REGION_DISTRICT) val tldDistrict: RegionDistrictView?,
    @Embedded(prefix = PX_TERRITORY_LD_LOCALITY) val tldLocality: LocalityView?,
    @Embedded(prefix = PX_TERRITORY_LD_LOCALITY_DISTRICT) val tLocalityDistrict: LocalityDistrictView?,

    @Embedded(prefix = PX_TERRITORY_M_REGION) val tmRegion: GeoRegionView?,
    @Embedded(prefix = PX_TERRITORY_M_REGION_DISTRICT) val tmDistrict: RegionDistrictView?,
    @Embedded(prefix = PX_TERRITORY_M_LOCALITY) val tmLocality: LocalityView?,
    @Embedded(prefix = PX_TERRITORY_M_LOCALITY_DISTRICT) val tmLocalityDistrict: LocalityDistrictView?,
    @Embedded(prefix = PX_TERRITORY_MICRODISTRICT) val tMicrodistrict: MicrodistrictView?,

    val territoryBusinessMark: String
) {
    companion object {
        const val VIEW_NAME = "territories_view"
    }
}