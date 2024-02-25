package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity

@DatabaseView(
    viewName = GeoLocalityView.VIEW_NAME,
    value = """
SELECT nv.countryId AS ${GeoCountryEntity.PX}countryId, nv.countryCode AS ${GeoCountryEntity.PX}countryCode, 
            nv.countryGeocode AS ${GeoCountryEntity.PX}countryGeocode, nv.countryOsmId AS ${GeoCountryEntity.PX}countryOsmId, 
            nv.${GeoCountryEntity.PREFIX}latitude AS ${GeoCountryEntity.PX}${GeoCountryEntity.PREFIX}latitude,
            nv.${GeoCountryEntity.PREFIX}longitude AS ${GeoCountryEntity.PX}${GeoCountryEntity.PREFIX}longitude,
            nv.countryTlId AS ${GeoCountryEntity.PX}countryTlId, nv.countryLocCode AS ${GeoCountryEntity.PX}countryLocCode, nv.countryTlCode AS ${GeoCountryEntity.PX}countryTlCode,  
            nv.countryName AS ${GeoCountryEntity.PX}countryName, nv.countriesId AS ${GeoCountryEntity.PX}countriesId,
        rv.regionId AS ${GeoRegionEntity.PX}regionId, rv.regionCode AS ${GeoRegionEntity.PX}regionCode, rv.regionType AS ${GeoRegionEntity.PX}regionType,
            rv.regionGeocode AS ${GeoRegionEntity.PX}regionGeocode, rv.regionOsmId AS ${GeoRegionEntity.PX}regionOsmId, 
            rv.${GeoRegionEntity.PREFIX}latitude AS ${GeoRegionEntity.PX}${GeoRegionEntity.PREFIX}latitude,
            rv.${GeoRegionEntity.PREFIX}longitude AS ${GeoRegionEntity.PX}${GeoRegionEntity.PREFIX}longitude, 
            rv.rCountriesId AS ${GeoRegionEntity.PX}rCountriesId, 
            rv.regionTlId AS ${GeoRegionEntity.PX}regionTlId, rv.regionLocCode AS ${GeoRegionEntity.PX}regionLocCode, rv.regionTlCode AS ${GeoRegionEntity.PX}regionTlCode, 
            rv.regionName AS ${GeoRegionEntity.PX}regionName, rv.regionsId AS ${GeoRegionEntity.PX}regionsId, 
        rdv.regionDistrictId AS ${GeoRegionDistrictEntity.PX}regionDistrictId, rdv.regDistrictShortName  AS ${GeoRegionDistrictEntity.PX}regDistrictShortName, 
            rdv.regDistrictGeocode AS ${GeoRegionDistrictEntity.PX}regDistrictGeocode, rdv.regDistrictOsmId AS ${GeoRegionDistrictEntity.PX}regDistrictOsmId, 
            rdv.${GeoRegionDistrictEntity.PREFIX}latitude AS ${GeoRegionDistrictEntity.PX}${GeoRegionDistrictEntity.PREFIX}latitude,
            rdv.${GeoRegionDistrictEntity.PREFIX}longitude AS ${GeoRegionDistrictEntity.PX}${GeoRegionDistrictEntity.PREFIX}longitude, 
            rdv.rRegionsId  AS ${GeoRegionDistrictEntity.PX}rRegionsId,
            rdv.regionDistrictTlId  AS ${GeoRegionDistrictEntity.PX}regionDistrictTlId, rdv.regDistrictLocCode  AS ${GeoRegionDistrictEntity.PX}regDistrictLocCode,
            rdv.regDistrictTlShortName AS ${GeoRegionDistrictEntity.PX}regDistrictTlShortName, 
            rdv.regDistrictName  AS ${GeoRegionDistrictEntity.PX}regDistrictName, rdv.regionDistrictsId  AS ${GeoRegionDistrictEntity.PX}regionDistrictsId, 
        lv.localityId AS ${GeoLocalityEntity.PX}localityId, lv.localityCode AS ${GeoLocalityEntity.PX}localityCode, lv.localityType AS ${GeoLocalityEntity.PX}localityType, 
            lv.localityGeocode AS ${GeoLocalityEntity.PX}localityGeocode, lv.localityOsmId AS ${GeoLocalityEntity.PX}localityOsmId, 
            lv.${GeoLocalityEntity.PREFIX}latitude AS ${GeoLocalityEntity.PX}${GeoLocalityEntity.PREFIX}latitude,
            lv.${GeoLocalityEntity.PREFIX}longitude AS ${GeoLocalityEntity.PX}${GeoLocalityEntity.PREFIX}longitude, 
            lv.lRegionDistrictsId AS ${GeoLocalityEntity.PX}lRegionDistrictsId, lv.lRegionsId AS ${GeoLocalityEntity.PX}lRegionsId,
            lv.localityTlId AS ${GeoLocalityEntity.PX}localityTlId, lv.localityLocCode AS ${GeoLocalityEntity.PX}localityLocCode, lv.localityShortName AS ${GeoLocalityEntity.PX}localityShortName, 
            lv.localityName AS ${GeoLocalityEntity.PX}localityName, lv.localitiesId AS ${GeoLocalityEntity.PX}localitiesId
    FROM ${LocalityView.VIEW_NAME} lv JOIN ${RegionView.VIEW_NAME} rv ON rv.regionId = lv.lRegionsId AND rv.regionLocCode = lv.localityLocCode
        JOIN ${GeoCountryView.VIEW_NAME} nv ON nv.countryId = rv.rCountriesId AND nv.countryLocCode = lv.localityLocCode
        LEFT JOIN ${RegionDistrictView.VIEW_NAME} rdv ON rdv.regionDistrictId = lv.lRegionDistrictsId AND rdv.regDistrictLocCode = lv.localityLocCode
"""
)
class GeoLocalityView(
    @Embedded(prefix = GeoCountryEntity.PX) val country: GeoCountryView,
    @Embedded(prefix = GeoRegionEntity.PX) val region: RegionView,
    @Embedded(prefix = GeoRegionDistrictEntity.PX) val district: RegionDistrictView?,
    @Embedded(prefix = GeoLocalityEntity.PX) val locality: LocalityView
) {
    companion object {
        const val VIEW_NAME = "geo_localities_view"
    }
}