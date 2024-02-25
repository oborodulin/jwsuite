package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity

@DatabaseView(
    viewName = GeoLocalityDistrictView.VIEW_NAME,
    value = """
SELECT nv.countryId AS ${GeoCountryEntity.PX_LOCALITY_DISTRICT}countryId, nv.countryCode AS ${GeoCountryEntity.PX_LOCALITY_DISTRICT}countryCode, 
            nv.countryGeocode AS ${GeoCountryEntity.PX_LOCALITY_DISTRICT}countryGeocode, nv.countryOsmId AS ${GeoCountryEntity.PX_LOCALITY_DISTRICT}countryOsmId, 
            nv.${GeoCountryEntity.PREFIX}latitude AS ${GeoCountryEntity.PX_LOCALITY_DISTRICT}${GeoCountryEntity.PREFIX}latitude,
            nv.${GeoCountryEntity.PREFIX}longitude AS ${GeoCountryEntity.PX_LOCALITY_DISTRICT}${GeoCountryEntity.PREFIX}longitude,
            nv.countryTlId AS ${GeoCountryEntity.PX_LOCALITY_DISTRICT}countryTlId, nv.countryLocCode AS ${GeoCountryEntity.PX_LOCALITY_DISTRICT}countryLocCode, nv.countryTlCode AS ${GeoCountryEntity.PX_LOCALITY_DISTRICT}countryTlCode,  
            nv.countryName AS ${GeoCountryEntity.PX_LOCALITY_DISTRICT}countryName, nv.countriesId AS ${GeoCountryEntity.PX_LOCALITY_DISTRICT}countriesId,
        rv.regionId AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}regionId, rv.regionCode AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}regionCode, rv.regionType AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}regionType,
            rv.regionGeocode AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}regionGeocode, rv.regionOsmId AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}regionOsmId, 
            rv.${GeoRegionEntity.PREFIX}latitude AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}${GeoRegionEntity.PREFIX}latitude,
            rv.${GeoRegionEntity.PREFIX}longitude AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}${GeoRegionEntity.PREFIX}longitude, 
            rv.rCountriesId AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}rCountriesId, 
            rv.regionTlId AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}regionTlId, rv.regionLocCode AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}regionLocCode, rv.regionTlCode AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}regionTlCode, 
            rv.regionName AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}regionName, rv.regionsId AS ${GeoRegionEntity.PX_LOCALITY_DISTRICT}regionsId, 
        rdv.regionDistrictId AS ${GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT}regionDistrictId, rdv.regDistrictShortName  AS ${GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT}regDistrictShortName, 
            rdv.regDistrictGeocode AS ${GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT}regDistrictGeocode, rdv.regDistrictOsmId AS ${GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT}regDistrictOsmId, 
            rdv.${GeoRegionDistrictEntity.PREFIX}latitude AS ${GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT}${GeoRegionDistrictEntity.PREFIX}latitude,
            rdv.${GeoRegionDistrictEntity.PREFIX}longitude AS ${GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT}${GeoRegionDistrictEntity.PREFIX}longitude, 
            rdv.rRegionsId AS ${GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT}rRegionsId,
            rdv.regionDistrictTlId  AS ${GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT}regionDistrictTlId, rdv.regDistrictLocCode  AS ${GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT}regDistrictLocCode,
            rdv.regDistrictTlShortName AS ${GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT}regDistrictTlShortName,
            rdv.regDistrictName  AS ${GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT}regDistrictName, rdv.regionDistrictsId  AS ${GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT}regionDistrictsId, 
        lv.localityId AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}localityId, lv.localityCode AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}localityCode, lv.localityType AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}localityType, 
            lv.localityGeocode AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}localityGeocode, lv.localityOsmId AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}localityOsmId, 
            lv.${GeoLocalityEntity.PREFIX}latitude AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}${GeoLocalityEntity.PREFIX}latitude,
            lv.${GeoLocalityEntity.PREFIX}longitude AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}${GeoLocalityEntity.PREFIX}longitude, 
            lv.lRegionDistrictsId AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}lRegionDistrictsId, lv.lRegionsId AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}lRegionsId,
            lv.localityTlId AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}localityTlId, lv.localityLocCode AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}localityLocCode, lv.localityShortName AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}localityShortName, 
            lv.localityName AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}localityName, lv.localitiesId AS ${GeoLocalityEntity.PX_LOCALITY_DISTRICT}localitiesId, 
        ldv.localityDistrictId AS ${GeoLocalityDistrictEntity.PX}localityDistrictId, ldv.locDistrictShortName AS ${GeoLocalityDistrictEntity.PX}locDistrictShortName, 
            ldv.locDistrictGeocode AS ${GeoLocalityDistrictEntity.PX}locDistrictGeocode, ldv.locDistrictOsmId AS ${GeoLocalityDistrictEntity.PX}locDistrictOsmId, 
            ldv.${GeoLocalityDistrictEntity.PREFIX}latitude AS ${GeoLocalityDistrictEntity.PX}${GeoLocalityDistrictEntity.PREFIX}latitude,
            ldv.${GeoLocalityDistrictEntity.PREFIX}longitude AS ${GeoLocalityDistrictEntity.PX}${GeoLocalityDistrictEntity.PREFIX}longitude, 
            ldv.ldLocalitiesId AS ${GeoLocalityDistrictEntity.PX}ldLocalitiesId, ldv.localityDistrictTlId AS ${GeoLocalityDistrictEntity.PX}localityDistrictTlId, 
            ldv.locDistrictLocCode AS ${GeoLocalityDistrictEntity.PX}locDistrictLocCode, ldv.locDistrictName AS ${GeoLocalityDistrictEntity.PX}locDistrictName, 
            ldv.localityDistrictsId AS ${GeoLocalityDistrictEntity.PX}localityDistrictsId
FROM ${LocalityDistrictView.VIEW_NAME} ldv JOIN ${LocalityView.VIEW_NAME} lv ON lv.localityId = ldv.ldLocalitiesId AND lv.localityLocCode = ldv.locDistrictLocCode 
    JOIN ${RegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = ldv.locDistrictLocCode
    JOIN ${GeoCountryView.VIEW_NAME} nv ON nv.countryId = rv.rCountriesId AND nv.countryLocCode = ldv.locDistrictLocCode
    LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rdv.regDistrictLocCode = ldv.locDistrictLocCode
"""
)
class GeoLocalityDistrictView(
    @Embedded(prefix = GeoCountryEntity.PX_LOCALITY_DISTRICT) val country: GeoCountryView,
    @Embedded(prefix = GeoRegionEntity.PX_LOCALITY_DISTRICT) val region: RegionView,
    @Embedded(prefix = GeoRegionDistrictEntity.PX_LOCALITY_DISTRICT) val district: RegionDistrictView?,
    @Embedded(prefix = GeoLocalityEntity.PX_LOCALITY_DISTRICT) val locality: LocalityView,
    @Embedded(prefix = GeoLocalityDistrictEntity.PX) val localityDistrict: LocalityDistrictView
) {
    companion object {
        const val VIEW_NAME = "geo_locality_districts_view"
    }
}