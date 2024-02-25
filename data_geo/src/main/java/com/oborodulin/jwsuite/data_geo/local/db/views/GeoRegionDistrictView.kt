package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity

@DatabaseView(
    viewName = GeoRegionDistrictView.VIEW_NAME,
    value = """
SELECT nv.countryId AS ${GeoCountryEntity.PX_DISTRICT}countryId, nv.countryCode AS ${GeoCountryEntity.PX_DISTRICT}countryCode, 
            nv.countryGeocode AS ${GeoCountryEntity.PX_DISTRICT}countryGeocode, nv.countryOsmId AS ${GeoCountryEntity.PX_DISTRICT}countryOsmId, 
            nv.${GeoCountryEntity.PREFIX}latitude AS ${GeoCountryEntity.PX_DISTRICT}${GeoCountryEntity.PREFIX}latitude,
            nv.${GeoCountryEntity.PREFIX}longitude AS ${GeoCountryEntity.PX_DISTRICT}${GeoCountryEntity.PREFIX}longitude,
            nv.countryTlId AS ${GeoCountryEntity.PX_DISTRICT}countryTlId, nv.countryLocCode AS ${GeoCountryEntity.PX_DISTRICT}countryLocCode, nv.countryTlCode AS ${GeoCountryEntity.PX_DISTRICT}countryTlCode,  
            nv.countryName AS ${GeoCountryEntity.PX_DISTRICT}countryName, nv.countriesId AS ${GeoCountryEntity.PX_DISTRICT}countriesId,
        rv.regionId AS ${GeoRegionEntity.PX_DISTRICT}regionId, rv.regionCode AS ${GeoRegionEntity.PX_DISTRICT}regionCode, rv.regionType AS ${GeoRegionEntity.PX_DISTRICT}regionType,
            rv.regionGeocode AS ${GeoRegionEntity.PX_DISTRICT}regionGeocode, rv.regionOsmId AS ${GeoRegionEntity.PX_DISTRICT}regionOsmId, 
            rv.${GeoRegionEntity.PREFIX}latitude AS ${GeoRegionEntity.PX_DISTRICT}${GeoRegionEntity.PREFIX}latitude,
            rv.${GeoRegionEntity.PREFIX}longitude AS ${GeoRegionEntity.PX_DISTRICT}${GeoRegionEntity.PREFIX}longitude, 
            rv.rCountriesId AS ${GeoRegionEntity.PX_DISTRICT}rCountriesId,
            rv.regionTlId AS ${GeoRegionEntity.PX_DISTRICT}regionTlId, rv.regionLocCode AS ${GeoRegionEntity.PX_DISTRICT}regionLocCode, rv.regionTlCode AS ${GeoRegionEntity.PX_DISTRICT}regionTlCode,  
            rv.regionName AS ${GeoRegionEntity.PX_DISTRICT}regionName, rv.regionsId AS ${GeoRegionEntity.PX_DISTRICT}regionsId, 
        rdv.regionDistrictId AS ${GeoRegionDistrictEntity.PX}regionDistrictId, rdv.regDistrictShortName  AS ${GeoRegionDistrictEntity.PX}regDistrictShortName, 
            rdv.regDistrictGeocode AS ${GeoRegionDistrictEntity.PX}regDistrictGeocode, rdv.regDistrictOsmId AS ${GeoRegionDistrictEntity.PX}regDistrictOsmId, 
            rdv.${GeoRegionDistrictEntity.PREFIX}latitude AS ${GeoRegionDistrictEntity.PX}${GeoRegionDistrictEntity.PREFIX}latitude,
            rdv.${GeoRegionDistrictEntity.PREFIX}longitude AS ${GeoRegionDistrictEntity.PX}${GeoRegionDistrictEntity.PREFIX}longitude, 
            rdv.rRegionsId AS ${GeoRegionDistrictEntity.PX}rRegionsId,
            rdv.regionDistrictTlId  AS ${GeoRegionDistrictEntity.PX}regionDistrictTlId, rdv.regDistrictLocCode  AS ${GeoRegionDistrictEntity.PX}regDistrictLocCode,
            rdv.regDistrictTlShortName AS ${GeoRegionDistrictEntity.PX}regDistrictTlShortName,
            rdv.regDistrictName  AS ${GeoRegionDistrictEntity.PX}regDistrictName, rdv.regionDistrictsId  AS ${GeoRegionDistrictEntity.PX}regionDistrictsId
    FROM ${RegionDistrictView.VIEW_NAME} rdv JOIN ${RegionView.VIEW_NAME} rv ON rv.regionId = rdv.rRegionsId AND rv.regionLocCode = rdv.regDistrictLocCode
        JOIN ${GeoCountryView.VIEW_NAME} nv ON nv.countryId = rv.rCountriesId AND nv.countryLocCode = rdv.regDistrictLocCode
"""
)
class GeoRegionDistrictView(
    @Embedded(prefix = GeoCountryEntity.PX_DISTRICT) val country: GeoCountryView,
    @Embedded(prefix = GeoRegionEntity.PX_DISTRICT) val region: RegionView,
    @Embedded(prefix = GeoRegionDistrictEntity.PX) val district: RegionDistrictView
) {
    companion object {
        const val VIEW_NAME = "geo_region_districts_view"
    }
}