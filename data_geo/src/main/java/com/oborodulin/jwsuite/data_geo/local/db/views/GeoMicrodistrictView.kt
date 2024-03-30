package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity

@DatabaseView(
    viewName = GeoMicrodistrictView.VIEW_NAME,
    value = """
SELECT nv.countryId AS ${GeoCountryEntity.PX_MICRODISTRICT}countryId, nv.countryCode AS ${GeoCountryEntity.PX_MICRODISTRICT}countryCode, 
            nv.countryGeocode AS ${GeoCountryEntity.PX_MICRODISTRICT}countryGeocode, nv.countryOsmId AS ${GeoCountryEntity.PX_MICRODISTRICT}countryOsmId, 
            nv.${GeoCountryEntity.PREFIX}latitude AS ${GeoCountryEntity.PX_MICRODISTRICT}${GeoCountryEntity.PREFIX}latitude,
            nv.${GeoCountryEntity.PREFIX}longitude AS ${GeoCountryEntity.PX_MICRODISTRICT}${GeoCountryEntity.PREFIX}longitude,
            nv.countryTlId AS ${GeoCountryEntity.PX_MICRODISTRICT}countryTlId, nv.countryLocCode AS ${GeoCountryEntity.PX_MICRODISTRICT}countryLocCode, nv.countryTlCode AS ${GeoCountryEntity.PX_MICRODISTRICT}countryTlCode,  
            nv.countryName AS ${GeoCountryEntity.PX_MICRODISTRICT}countryName, nv.countriesId AS ${GeoCountryEntity.PX_MICRODISTRICT}countriesId,
        rv.regionId AS ${GeoRegionEntity.PX_MICRODISTRICT}regionId, rv.regionCode AS ${GeoRegionEntity.PX_MICRODISTRICT}regionCode, rv.regionType AS ${GeoRegionEntity.PX_MICRODISTRICT}regionType,
            rv.isRegionTypePrefix AS ${GeoRegionEntity.PX_MICRODISTRICT}isRegionTypePrefix,
            rv.regionGeocode AS ${GeoRegionEntity.PX_MICRODISTRICT}regionGeocode, rv.regionOsmId AS ${GeoRegionEntity.PX_MICRODISTRICT}regionOsmId, 
            rv.${GeoRegionEntity.PREFIX}latitude AS ${GeoRegionEntity.PX_MICRODISTRICT}${GeoRegionEntity.PREFIX}latitude,
            rv.${GeoRegionEntity.PREFIX}longitude AS ${GeoRegionEntity.PX_MICRODISTRICT}${GeoRegionEntity.PREFIX}longitude,
            rv.rCountriesId AS ${GeoRegionEntity.PX_MICRODISTRICT}rCountriesId,
            rv.regionTlId AS ${GeoRegionEntity.PX_MICRODISTRICT}regionTlId, rv.regionLocCode AS ${GeoRegionEntity.PX_MICRODISTRICT}regionLocCode, rv.regionTlCode AS ${GeoRegionEntity.PX_MICRODISTRICT}regionTlCode,
            rv.regionName AS ${GeoRegionEntity.PX_MICRODISTRICT}regionName, rv.regionsId AS ${GeoRegionEntity.PX_MICRODISTRICT}regionsId, 
        rdv.regionDistrictId AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}regionDistrictId, rdv.regDistrictShortName AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}regDistrictShortName,
            rdv.regDistrictType AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}regDistrictType,
            rdv.regDistrictGeocode AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}regDistrictGeocode, rdv.regDistrictOsmId AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}regDistrictOsmId, 
            rdv.${GeoRegionDistrictEntity.PREFIX}latitude AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}${GeoRegionDistrictEntity.PREFIX}latitude,
            rdv.${GeoRegionDistrictEntity.PREFIX}longitude AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}${GeoRegionDistrictEntity.PREFIX}longitude, 
            rdv.rRegionsId AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}rRegionsId,
            rdv.regionDistrictTlId AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}regionDistrictTlId, rdv.regDistrictLocCode AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}regDistrictLocCode,
            rdv.regDistrictTlShortName AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}regDistrictTlShortName, 
            rdv.regDistrictName AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}regDistrictName, rdv.regionDistrictsId AS ${GeoRegionDistrictEntity.PX_MICRODISTRICT}regionDistrictsId, 
        lv.localityId AS ${GeoLocalityEntity.PX_MICRODISTRICT}localityId, lv.localityCode AS ${GeoLocalityEntity.PX_MICRODISTRICT}localityCode, lv.localityType AS ${GeoLocalityEntity.PX_MICRODISTRICT}localityType, 
            lv.localityGeocode AS ${GeoLocalityEntity.PX_MICRODISTRICT}localityGeocode, lv.localityOsmId AS ${GeoLocalityEntity.PX_MICRODISTRICT}localityOsmId, 
            lv.${GeoLocalityEntity.PREFIX}latitude AS ${GeoLocalityEntity.PX_MICRODISTRICT}${GeoLocalityEntity.PREFIX}latitude,
            lv.${GeoLocalityEntity.PREFIX}longitude AS ${GeoLocalityEntity.PX_MICRODISTRICT}${GeoLocalityEntity.PREFIX}longitude, 
            lv.lRegionDistrictsId AS ${GeoLocalityEntity.PX_MICRODISTRICT}lRegionDistrictsId, lv.lRegionsId AS ${GeoLocalityEntity.PX_MICRODISTRICT}lRegionsId,
            lv.localityTlId AS ${GeoLocalityEntity.PX_MICRODISTRICT}localityTlId, lv.localityLocCode AS ${GeoLocalityEntity.PX_MICRODISTRICT}localityLocCode, lv.localityShortName AS ${GeoLocalityEntity.PX_MICRODISTRICT}localityShortName, 
            lv.localityName AS ${GeoLocalityEntity.PX_MICRODISTRICT}localityName, lv.localitiesId AS ${GeoLocalityEntity.PX_MICRODISTRICT}localitiesId, 
        ldv.localityDistrictId AS ${GeoLocalityDistrictEntity.PX_MICRODISTRICT}localityDistrictId, ldv.locDistrictShortName AS ${GeoLocalityDistrictEntity.PX_MICRODISTRICT}locDistrictShortName, 
            ldv.locDistrictGeocode AS ${GeoLocalityDistrictEntity.PX_MICRODISTRICT}locDistrictGeocode, ldv.locDistrictOsmId AS ${GeoLocalityDistrictEntity.PX_MICRODISTRICT}locDistrictOsmId, 
            ldv.${GeoLocalityDistrictEntity.PREFIX}latitude AS ${GeoLocalityDistrictEntity.PX_MICRODISTRICT}${GeoLocalityDistrictEntity.PREFIX}latitude,
            ldv.${GeoLocalityDistrictEntity.PREFIX}longitude AS ${GeoLocalityDistrictEntity.PX_MICRODISTRICT}${GeoLocalityDistrictEntity.PREFIX}longitude, 
            ldv.ldLocalitiesId AS ${GeoLocalityDistrictEntity.PX_MICRODISTRICT}ldLocalitiesId, ldv.localityDistrictTlId AS ${GeoLocalityDistrictEntity.PX_MICRODISTRICT}localityDistrictTlId, 
            ldv.locDistrictLocCode AS ${GeoLocalityDistrictEntity.PX_MICRODISTRICT}locDistrictLocCode, ldv.locDistrictName AS ${GeoLocalityDistrictEntity.PX_MICRODISTRICT}locDistrictName, 
            ldv.localityDistrictsId AS ${GeoLocalityDistrictEntity.PX_MICRODISTRICT}localityDistrictsId, 
        mdv.* 
FROM ${MicrodistrictView.VIEW_NAME} mdv JOIN ${LocalityDistrictView.VIEW_NAME} ldv ON ldv.localityDistrictId = mdv.mLocalityDistrictsId AND ldv.locDistrictLocCode = mdv.microdistrictLocCode 
    JOIN ${LocalityView.VIEW_NAME} lv ON lv.localityId = ldv.ldLocalitiesId AND lv.localityLocCode = mdv.microdistrictLocCode
    JOIN ${RegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = mdv.microdistrictLocCode
    JOIN ${GeoCountryView.VIEW_NAME} nv ON nv.countryId = rv.rCountriesId AND nv.countryLocCode = mdv.microdistrictLocCode
    LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rdv.regDistrictLocCode = mdv.microdistrictLocCode
"""
)
class GeoMicrodistrictView(
    @Embedded(prefix = GeoCountryEntity.PX_MICRODISTRICT) val country: GeoCountryView,
    @Embedded(prefix = GeoRegionEntity.PX_MICRODISTRICT) val region: RegionView,
    @Embedded(prefix = GeoRegionDistrictEntity.PX_MICRODISTRICT) val district: RegionDistrictView?,
    @Embedded(prefix = GeoLocalityEntity.PX_MICRODISTRICT) val locality: LocalityView,
    @Embedded(prefix = GeoLocalityDistrictEntity.PX_MICRODISTRICT) val localityDistrict: LocalityDistrictView,
    @Embedded val microdistrict: MicrodistrictView
) {
    companion object {
        const val VIEW_NAME = "geo_microdistricts_view"
    }
}