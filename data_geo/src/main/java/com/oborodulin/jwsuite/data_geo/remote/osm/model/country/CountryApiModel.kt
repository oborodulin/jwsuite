package com.oborodulin.jwsuite.data_geo.remote.osm.model.country

import com.oborodulin.jwsuite.data_geo.remote.osm.model.Geometry
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Osm3s
import com.oborodulin.jwsuite.domain.util.Constants.OSM_TIMEOUT
import com.squareup.moshi.Json
import java.util.Locale

data class CountryApiModel(
    @Json(name = "version") val version: String,
    @Json(name = "generator") val generator: String,
    @Json(name = "osm3s") val osm3s: Osm3s,
    @Json(name = "elements") val elements: List<CountryElement>
) {
    companion object {
        fun data(locale: String? = Locale.getDefault().language.substringBefore('-')) = """
    [out:json][timeout:$OSM_TIMEOUT];
    (rel[admin_level="2"][boundary="administrative"][type!="multilinestring"][~"^(country_code_iso3166_1_alpha_2|ISO3166-1:alpha2)${'$'}"~"."];)->.rc;
    foreach.rc(
        convert CountryType 
            osmType = type(), ::id = id(), ::geom = geom(), countryCode = t["country_code_iso3166_1_alpha_2"], isoCode = t["ISO3166-1:alpha2"], geocodeArea = t["name:en"], locale = "$locale", name_loc = t["name:$locale"], name = t["name"], flag = t["flag"];
	    out center;
    );
    """.trimIndent()
    }
}

data class CountryElement(
    @Json(name = "type") val type: String,
    @Json(name = "id") val id: Long,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "tags") val tags: CountryTags
)

data class CountryTags(
    @Json(name = "osmType") val osmType: String,
    @Json(name = "countryCode") val countryCode: String,
    @Json(name = "isoCode") val isoCode: String,
    @Json(name = "geocodeArea") val geocodeArea: String,
    @Json(name = "locale") val locale: String,
    // GeoCountry.countryName
    @Json(name = "name_loc") val nameLoc: String,
    @Json(name = "name") val name: String,
    @Json(name = "flag") val flag: String
)