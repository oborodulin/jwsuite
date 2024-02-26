package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationView
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityDistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityView
import com.oborodulin.jwsuite.data_geo.local.db.views.MicrodistrictView
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.domain.util.Constants.PRM_TERRITORY_BUSINESS_MARK_VAL

@DatabaseView(
    viewName = TerritoryView.VIEW_NAME,
    value = """
SELECT t.*, cv.*, tc.*,
        lv.localityId AS ${TerritoryEntity.PX_LOCALITY}localityId, lv.localityCode AS ${TerritoryEntity.PX_LOCALITY}localityCode, lv.localityType AS ${TerritoryEntity.PX_LOCALITY}localityType, 
            lv.localityGeocode AS ${TerritoryEntity.PX_LOCALITY}localityGeocode, lv.localityOsmId AS ${TerritoryEntity.PX_LOCALITY}localityOsmId, 
            lv.${GeoLocalityEntity.PREFIX}latitude AS ${TerritoryEntity.PX_LOCALITY}${GeoLocalityEntity.PREFIX}latitude,
            lv.${GeoLocalityEntity.PREFIX}longitude AS ${TerritoryEntity.PX_LOCALITY}${GeoLocalityEntity.PREFIX}longitude, 
            lv.lRegionDistrictsId AS ${TerritoryEntity.PX_LOCALITY}lRegionDistrictsId, lv.lRegionsId AS ${TerritoryEntity.PX_LOCALITY}lRegionsId,
            lv.localityTlId AS ${TerritoryEntity.PX_LOCALITY}localityTlId, lv.localityLocCode AS ${TerritoryEntity.PX_LOCALITY}localityLocCode, lv.localityShortName AS ${TerritoryEntity.PX_LOCALITY}localityShortName, 
            lv.localityName AS ${TerritoryEntity.PX_LOCALITY}localityName, lv.localitiesId AS ${TerritoryEntity.PX_LOCALITY}localitiesId,
        ldv.localityDistrictId AS ${TerritoryEntity.PX_LD_LOCALITY_DISTRICT}localityDistrictId, ldv.locDistrictShortName AS ${TerritoryEntity.PX_LD_LOCALITY_DISTRICT}locDistrictShortName, 
            ldv.locDistrictGeocode AS ${TerritoryEntity.PX_LD_LOCALITY_DISTRICT}locDistrictGeocode, ldv.locDistrictOsmId AS ${TerritoryEntity.PX_LD_LOCALITY_DISTRICT}locDistrictOsmId, 
            ldv.${GeoLocalityDistrictEntity.PREFIX}latitude AS ${TerritoryEntity.PX_LD_LOCALITY_DISTRICT}${GeoLocalityDistrictEntity.PREFIX}latitude,
            ldv.${GeoLocalityDistrictEntity.PREFIX}longitude AS ${TerritoryEntity.PX_LD_LOCALITY_DISTRICT}${GeoLocalityDistrictEntity.PREFIX}longitude, 
            ldv.ldLocalitiesId AS ${TerritoryEntity.PX_LD_LOCALITY_DISTRICT}ldLocalitiesId, ldv.localityDistrictTlId AS ${TerritoryEntity.PX_LD_LOCALITY_DISTRICT}localityDistrictTlId, 
            ldv.locDistrictLocCode AS ${TerritoryEntity.PX_LD_LOCALITY_DISTRICT}locDistrictLocCode, ldv.locDistrictName AS ${TerritoryEntity.PX_LD_LOCALITY_DISTRICT}locDistrictName, 
            ldv.localityDistrictsId AS ${TerritoryEntity.PX_LD_LOCALITY_DISTRICT}localityDistrictsId, 
        mdv.microdistrictId AS ${TerritoryEntity.PX_MICRODISTRICT}microdistrictId, mdv.microdistrictType AS ${TerritoryEntity.PX_MICRODISTRICT}microdistrictType, 
            mdv.microdistrictShortName AS ${TerritoryEntity.PX_MICRODISTRICT}microdistrictShortName,
            mdv.microdistrictGeocode AS ${TerritoryEntity.PX_MICRODISTRICT}microdistrictGeocode, mdv.microdistrictOsmId AS ${TerritoryEntity.PX_MICRODISTRICT}microdistrictOsmId, 
            mdv.${GeoMicrodistrictEntity.PREFIX}latitude AS ${TerritoryEntity.PX_MICRODISTRICT}${GeoMicrodistrictEntity.PREFIX}latitude,
            mdv.${GeoMicrodistrictEntity.PREFIX}longitude AS ${TerritoryEntity.PX_MICRODISTRICT}${GeoMicrodistrictEntity.PREFIX}longitude, 
            mdv.mLocalityDistrictsId AS ${TerritoryEntity.PX_MICRODISTRICT}mLocalityDistrictsId, 
            mdv.mLocalitiesId AS ${TerritoryEntity.PX_MICRODISTRICT}mLocalitiesId, mdv.microdistrictTlId AS ${TerritoryEntity.PX_MICRODISTRICT}microdistrictTlId, 
            mdv.microdistrictLocCode AS ${TerritoryEntity.PX_MICRODISTRICT}microdistrictLocCode, mdv.microdistrictName AS ${TerritoryEntity.PX_MICRODISTRICT}microdistrictName, 
            mdv.microdistrictsId AS ${TerritoryEntity.PX_MICRODISTRICT}microdistrictsId,
        s.paramValue AS territoryBusinessMark
FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${CongregationView.VIEW_NAME} cv ON cv.congregationId = t.tCongregationsId
    JOIN ${TerritoryCategoryEntity.TABLE_NAME} tc ON tc.territoryCategoryId = t.tTerritoryCategoriesId
    JOIN ${LocalityView.VIEW_NAME} lv ON lv.localityId = t.tLocalitiesId AND lv.localityLocCode = cv.${CongregationEntity.PX_LOCALITY}localityLocCode
    LEFT JOIN ${LocalityDistrictView.VIEW_NAME} ldv ON ldv.localityDistrictId = t.tLocalityDistrictsId AND ldv.locDistrictLocCode = cv.${CongregationEntity.PX_LOCALITY}localityLocCode
    LEFT JOIN ${MicrodistrictView.VIEW_NAME} mdv ON mdv.microdistrictId = t.tMicrodistrictsId AND mdv.microdistrictLocCode = cv.${CongregationEntity.PX_LOCALITY}localityLocCode
    JOIN ${AppSettingEntity.TABLE_NAME} s ON s.paramName = $PRM_TERRITORY_BUSINESS_MARK_VAL
"""
)
class TerritoryView(
    @Embedded val territory: TerritoryEntity,
    @Embedded val congregation: CongregationView,
    @Embedded val territoryCategory: TerritoryCategoryEntity,
    @Embedded(prefix = TerritoryEntity.PX_LOCALITY) val locality: LocalityView,
    @Embedded(prefix = TerritoryEntity.PX_LD_LOCALITY_DISTRICT) val localityDistrict: LocalityDistrictView?,
    @Embedded(prefix = TerritoryEntity.PX_MICRODISTRICT) val microdistrict: MicrodistrictView?,
    val territoryBusinessMark: String
) {
    companion object {
        const val VIEW_NAME = "territories_view"
    }
}
/*
        rv.regionId AS ${TerritoryEntity.PX_REGION}regionId, rv.regionCode AS ${TerritoryEntity.PX_REGION}regionCode, 
            rv.regionTlId AS ${TerritoryEntity.PX_REGION}regionTlId, rv.regionLocCode AS ${TerritoryEntity.PX_REGION}regionLocCode, rv.regionTlCode AS ${TerritoryEntity.PX_REGION}regionTlCode,
            rv.regionName AS ${TerritoryEntity.PX_REGION}regionName, rv.regionsId AS ${TerritoryEntity.PX_REGION}regionsId, 
        rdv.regionDistrictId AS ${TerritoryEntity.PX_REGION_DISTRICT}regionDistrictId, rdv.regDistrictShortName AS ${TerritoryEntity.PX_REGION_DISTRICT}regDistrictShortName, 
            rdv.rRegionsId AS ${TerritoryEntity.PX_REGION_DISTRICT}rRegionsId,
            rdv.regionDistrictTlId AS ${TerritoryEntity.PX_REGION_DISTRICT}regionDistrictTlId, rdv.regDistrictLocCode AS ${TerritoryEntity.PX_REGION_DISTRICT}regDistrictLocCode,
            rdv.regDistrictTlShortName AS ${TerritoryEntity.PX_REGION_DISTRICT}regDistrictTlShortName, 
            rdv.regDistrictName AS ${TerritoryEntity.PX_REGION_DISTRICT}regDistrictName, rdv.regionDistrictsId AS ${TerritoryEntity.PX_REGION_DISTRICT}regionDistrictsId, 

        rvl.regionId AS ${TerritoryEntity.PX_LD_REGION}regionId, rvl.regionCode AS ${TerritoryEntity.PX_LD_REGION}regionCode, 
            rvl.regionTlId AS ${TerritoryEntity.PX_LD_REGION}regionTlId, rvl.regionLocCode AS ${TerritoryEntity.PX_LD_REGION}regionLocCode, rvl.regionTlCode AS ${TerritoryEntity.PX_LD_REGION}regionTlCode,
            rvl.regionName AS ${TerritoryEntity.PX_LD_REGION}regionName, rvl.regionsId AS ${TerritoryEntity.PX_LD_REGION}regionsId, 
        rdvl.regionDistrictId AS ${TerritoryEntity.PX_LD_REGION_DISTRICT}regionDistrictId, rdvl.regDistrictShortName AS ${TerritoryEntity.PX_LD_REGION_DISTRICT}regDistrictShortName, 
            rdvl.rRegionsId AS ${TerritoryEntity.PX_LD_REGION_DISTRICT}rRegionsId,
            rdvl.regionDistrictTlId AS ${TerritoryEntity.PX_LD_REGION_DISTRICT}regionDistrictTlId, rdvl.regDistrictLocCode AS ${TerritoryEntity.PX_LD_REGION_DISTRICT}regDistrictLocCode,
            rdvl.regDistrictTlShortName AS ${TerritoryEntity.PX_LD_REGION_DISTRICT}regDistrictTlShortName,
            rdvl.regDistrictName AS ${TerritoryEntity.PX_LD_REGION_DISTRICT}regDistrictName, rdvl.regionDistrictsId AS ${TerritoryEntity.PX_LD_REGION_DISTRICT}regionDistrictsId, 
        lvl.localityId AS ${TerritoryEntity.PX_LD_LOCALITY}localityId, lvl.localityCode AS ${TerritoryEntity.PX_LD_LOCALITY}localityCode, lvl.localityType AS ${TerritoryEntity.PX_LD_LOCALITY}localityType, 
            lvl.lRegionDistrictsId AS ${TerritoryEntity.PX_LD_LOCALITY}lRegionDistrictsId, lvl.lRegionsId AS ${TerritoryEntity.PX_LD_LOCALITY}lRegionsId,
            lvl.localityTlId AS ${TerritoryEntity.PX_LD_LOCALITY}localityTlId, lvl.localityLocCode AS ${TerritoryEntity.PX_LD_LOCALITY}localityLocCode, lvl.localityShortName AS ${TerritoryEntity.PX_LD_LOCALITY}localityShortName, 
            lvl.localityName AS ${TerritoryEntity.PX_LD_LOCALITY}localityName, lvl.localitiesId AS ${TerritoryEntity.PX_LD_LOCALITY}localitiesId, 

        rvm.regionId AS ${TerritoryEntity.PX_M_REGION}regionId, rvm.regionCode AS ${TerritoryEntity.PX_M_REGION}regionCode, 
            rvm.regionTlId AS ${TerritoryEntity.PX_M_REGION}regionTlId, rvm.regionLocCode AS ${TerritoryEntity.PX_M_REGION}regionLocCode, rvm.regionTlCode AS ${TerritoryEntity.PX_M_REGION}regionTlCode,
            rvm.regionName AS ${TerritoryEntity.PX_M_REGION}regionName, rvm.regionsId AS ${TerritoryEntity.PX_M_REGION}regionsId, 
        rdvm.regionDistrictId AS ${TerritoryEntity.PX_M_REGION_DISTRICT}regionDistrictId, rdvm.regDistrictShortName AS ${TerritoryEntity.PX_M_REGION_DISTRICT}regDistrictShortName, 
            rdvm.rRegionsId AS ${TerritoryEntity.PX_M_REGION_DISTRICT}rRegionsId,
            rdvm.regionDistrictTlId AS ${TerritoryEntity.PX_M_REGION_DISTRICT}regionDistrictTlId, rdvm.regDistrictLocCode AS ${TerritoryEntity.PX_M_REGION_DISTRICT}regDistrictLocCode,
            rdvm.regDistrictTlShortName AS ${TerritoryEntity.PX_M_REGION_DISTRICT}regDistrictTlShortName, 
            rdvm.regDistrictName AS ${TerritoryEntity.PX_M_REGION_DISTRICT}regDistrictName, rdvm.regionDistrictsId AS ${TerritoryEntity.PX_M_REGION_DISTRICT}regionDistrictsId, 
        lvm.localityId AS ${TerritoryEntity.PX_M_LOCALITY}localityId, lvm.localityCode AS ${TerritoryEntity.PX_M_LOCALITY}localityCode, lvm.localityType AS ${TerritoryEntity.PX_M_LOCALITY}localityType, 
            lvm.lRegionDistrictsId AS ${TerritoryEntity.PX_M_LOCALITY}lRegionDistrictsId, lvm.lRegionsId AS ${TerritoryEntity.PX_M_LOCALITY}lRegionsId,
            lvm.localityTlId AS ${TerritoryEntity.PX_M_LOCALITY}localityTlId, lvm.localityLocCode AS ${TerritoryEntity.PX_M_LOCALITY}localityLocCode, lvm.localityShortName AS ${TerritoryEntity.PX_M_LOCALITY}localityShortName, 
            lvm.localityName AS ${TerritoryEntity.PX_M_LOCALITY}localityName, lvm.localitiesId AS ${TerritoryEntity.PX_M_LOCALITY}localitiesId, 
        ldvm.localityDistrictId AS ${TerritoryEntity.PX_M_LOCALITY_DISTRICT}localityDistrictId, ldvm.locDistrictShortName AS ${TerritoryEntity.PX_M_LOCALITY_DISTRICT}locDistrictShortName, 
            ldvm.ldLocalitiesId AS ${TerritoryEntity.PX_M_LOCALITY_DISTRICT}ldLocalitiesId, ldvm.localityDistrictTlId AS ${TerritoryEntity.PX_M_LOCALITY_DISTRICT}localityDistrictTlId, 
            ldvm.locDistrictLocCode AS ${TerritoryEntity.PX_M_LOCALITY_DISTRICT}locDistrictLocCode, ldvm.locDistrictName AS ${TerritoryEntity.PX_M_LOCALITY_DISTRICT}locDistrictName, 
            ldvm.localityDistrictsId AS ${TerritoryEntity.PX_M_LOCALITY_DISTRICT}localityDistrictsId, 

        JOIN ${GeoRegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = lv.localityLocCode
        LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rdv.regDistrictLocCode = lv.localityLocCode

        LEFT JOIN ${LocalityView.VIEW_NAME} lvl ON lvl.localityId = ldvl.ldLocalitiesId AND lvl.localityLocCode = cv.${CongregationEntity.PX_LOCALITY}localityLocCode
        LEFT JOIN ${GeoRegionView.VIEW_NAME} rvl ON rvl.regionId = lvl.lRegionsId AND rvl.regionLocCode = cv.${CongregationEntity.PX_LOCALITY}localityLocCode
        LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdvl ON rdvl.regionDistrictId = lvl.lRegionDistrictsId AND rdvl.regDistrictLocCode = cv.${CongregationEntity.PX_LOCALITY}localityLocCode

        LEFT JOIN ${LocalityDistrictView.VIEW_NAME} ldvm ON ldvm.localityDistrictId = mdv.mLocalityDistrictsId AND ldvm.locDistrictLocCode = cv.${CongregationEntity.PX_LOCALITY}localityLocCode
        LEFT JOIN ${LocalityView.VIEW_NAME} lvm ON lvm.localityId = ldvm.ldLocalitiesId AND lvm.localityLocCode = cv.${CongregationEntity.PX_LOCALITY}localityLocCode
        LEFT JOIN ${GeoRegionView.VIEW_NAME} rvm ON rvm.regionId = lvm.lRegionsId AND rvm.regionLocCode = cv.${CongregationEntity.PX_LOCALITY}localityLocCode
        LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdvm ON rdvm.regionDistrictId = lvm.lRegionDistrictsId AND rdvm.regDistrictLocCode = cv.${CongregationEntity.PX_LOCALITY}localityLocCode

    @Embedded(prefix = TerritoryEntity.PX_REGION) val tRegion: GeoRegionView,
    @Embedded(prefix = TerritoryEntity.PX_REGION_DISTRICT) val tDistrict: RegionDistrictView?,

    @Embedded(prefix = TerritoryEntity.PX_LD_REGION) val tldRegion: GeoRegionView?,
    @Embedded(prefix = TerritoryEntity.PX_LD_REGION_DISTRICT) val tldDistrict: RegionDistrictView?,
    @Embedded(prefix = TerritoryEntity.PX_LD_LOCALITY) val tldLocality: LocalityView?,

    @Embedded(prefix = TerritoryEntity.PX_M_REGION) val tmRegion: GeoRegionView?,
    @Embedded(prefix = TerritoryEntity.PX_M_REGION_DISTRICT) val tmDistrict: RegionDistrictView?,
    @Embedded(prefix = TerritoryEntity.PX_M_LOCALITY) val tmLocality: LocalityView?,
    @Embedded(prefix = TerritoryEntity.PX_M_LOCALITY_DISTRICT) val tmLocalityDistrict: LocalityDistrictView?,
*/