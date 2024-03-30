package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity

@DatabaseView(
    viewName = GeoRegionView.VIEW_NAME,
    value = """
SELECT nv.countryId AS ${GeoCountryEntity.PX_REGION}countryId, nv.countryCode AS ${GeoCountryEntity.PX_REGION}countryCode, 
            nv.${GeoCountryEntity.PREFIX}geocode AS ${GeoCountryEntity.PX_REGION}${GeoCountryEntity.PREFIX}geocode,
            nv.${GeoCountryEntity.PREFIX}osmId AS ${GeoCountryEntity.PX_REGION}${GeoCountryEntity.PREFIX}osmId, 
            nv.${GeoCountryEntity.PREFIX}latitude AS ${GeoCountryEntity.PX_REGION}${GeoCountryEntity.PREFIX}latitude,
            nv.${GeoCountryEntity.PREFIX}longitude AS ${GeoCountryEntity.PX_REGION}${GeoCountryEntity.PREFIX}longitude,
            nv.countryTlId AS ${GeoCountryEntity.PX_REGION}countryTlId, nv.countryLocCode AS ${GeoCountryEntity.PX_REGION}countryLocCode, nv.countryTlCode AS ${GeoCountryEntity.PX_REGION}countryTlCode,  
            nv.countryName AS ${GeoCountryEntity.PX_REGION}countryName, nv.countriesId AS ${GeoCountryEntity.PX_REGION}countriesId, 
        rv.regionId AS ${GeoRegionEntity.PX}regionId, rv.regionCode AS ${GeoRegionEntity.PX}regionCode, rv.regionType AS ${GeoRegionEntity.PX}regionType,
            rv.isRegionTypePrefix AS ${GeoRegionEntity.PX}isRegionTypePrefix,
            rv.regionGeocode AS ${GeoRegionEntity.PX}regionGeocode, rv.regionOsmId AS ${GeoRegionEntity.PX}regionOsmId, 
            rv.${GeoRegionEntity.PREFIX}latitude AS ${GeoRegionEntity.PX}${GeoRegionEntity.PREFIX}latitude,
            rv.${GeoRegionEntity.PREFIX}longitude AS ${GeoRegionEntity.PX}${GeoRegionEntity.PREFIX}longitude, 
            rv.rCountriesId AS ${GeoRegionEntity.PX}rCountriesId,
            rv.regionTlId AS ${GeoRegionEntity.PX}regionTlId, rv.regionLocCode AS ${GeoRegionEntity.PX}regionLocCode, rv.regionTlCode AS ${GeoRegionEntity.PX}regionTlCode,  
            rv.regionName AS ${GeoRegionEntity.PX}regionName, rv.regionsId AS ${GeoRegionEntity.PX}regionsId
    FROM ${RegionView.VIEW_NAME} rv JOIN ${GeoCountryView.VIEW_NAME} nv ON nv.countryId = rv.rCountriesId AND nv.countryLocCode = rv.regionLocCode 
    """
)
class GeoRegionView(
    @Embedded(prefix = GeoCountryEntity.PX_REGION) val country: GeoCountryView,
    @Embedded(prefix = GeoRegionEntity.PX) val region: RegionView
) {
    companion object {
        const val VIEW_NAME = "geo_regions_view"
    }
}