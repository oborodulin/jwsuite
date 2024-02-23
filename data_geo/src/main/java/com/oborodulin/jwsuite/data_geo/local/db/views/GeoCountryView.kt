package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryTlEntity

@DatabaseView(
    viewName = GeoCountryView.VIEW_NAME,
    value = """
    SELECT c.countryId, ifnull(ctl.countryTlCode, c.countryCode) AS countryCode, c.countryGeocode,
            c.countryOsmId, c.${GeoCountryEntity.PREFIX}latitude, c.${GeoCountryEntity.PREFIX}longitude,
            ctl.countryTlId, ctl.countryLocCode, ctl.countryTlCode, ctl.countryName, ctl.countriesId 
    FROM ${GeoCountryEntity.TABLE_NAME} c JOIN ${GeoCountryTlEntity.TABLE_NAME} ctl ON ctl.countriesId = c.countryId
    """
)
class GeoCountryView(
    @Embedded val data: GeoCountryEntity,
    @Embedded val tl: GeoCountryTlEntity
) {
    companion object {
        const val VIEW_NAME = "geo_countries_view"
    }
}