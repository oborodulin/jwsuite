package com.oborodulin.jwsuite.data_geo.remote.osm.model

import com.oborodulin.home.common.util.Constants.LOC_DELIMITER
import com.oborodulin.home.common.util.Constants.RES_DELIMITER
import com.oborodulin.jwsuite.domain.util.Constants.OSM_TIMEOUT
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Locale
import java.util.UUID

data class RegionApiModel(
    @Json(name = "version") val version: String,
    @Json(name = "generator") val generator: String,
    @Json(name = "osm3s") val osm3s: Osm3s,
    @Json(name = "elements") val elements: List<RegionElement>
) {
    companion object {
        //Ukraine
        //Russia
        //United States
        // https://stackoverflow.com/questions/75780718/find-area-id-related-to-one-city-in-a-specific-country-on-overpass-turbo
        // {{geocodeArea:$geocodeArea}}
        // https://wiki.openstreetmap.org/wiki/Overpass_API/Overpass_QL#Map_way.2Frelation_to_area_.28map_to_area.29
        fun data(
            countryId: UUID,
            geocodeArea: String,
            countryCode: String,
            excRegionType: String,  // город федерального значения;містo зі спеціальним статусом
            locale: String? = Locale.getDefault().language.substringBefore(LOC_DELIMITER)
        ) = """
[out:json][timeout:$OSM_TIMEOUT];
(area["admin_level"="2"]["name:en"="$geocodeArea"];)->.searchArea;
(rel["admin_level"="4"]["place"!="city"][~"^(ISO3166-2|addr:country|is_in:country_code)+${'$'}"~"^$countryCode(.)*${'$'}",i]["official_status"!~"(${
            excRegionType.replace(RES_DELIMITER, '|')
        })"]["name"](area.searchArea);)->.rr;
foreach.rr(
    convert RegionType 
        osmType = type(), ::id = id(), ::geom = geom(), countryId = "$countryId", cadaster_code = t["cadaster:code"], short_name_loc = t["short_name:$locale"], ref_loc = t["ref:$locale"], gost = t["gost_7.67-2003"], short_name = t["short_name"], ref = t["ref"], isoCode = t["ISO3166-2"], official_name_loc = t["official_name:$locale"], official_name = t["official_name"], official_status = t["official_status"], geocodeArea = t["name:en"], locale = "$locale", name_loc = t["name:$locale"], name = t["name"], flag = t["flag"];
    out center;
);
    """.trimIndent()
    }
}

data class RegionElement(
    @Json(name = "type") val type: String,
    @Json(name = "id") val id: Long,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "tags") val tags: RegionTags
)

@JsonClass(generateAdapter = true)
data class RegionTags(
    @Json(name = "osmType") val osmType: String,
    @Json(name = "countryId") val countryId: UUID,
    // for GeoRegion.regionCode
    @Json(name = "cadaster_code") val cadasterCode: String,
    @Json(name = "short_name_loc") val shortNameLoc: String,
    @Json(name = "ref_loc") val refLoc: String,
    @Json(name = "gost") val gost: String,
    @Json(name = "short_name") val shortName: String,
    @Json(name = "ref") val ref: String,
    @Json(name = "isoCode") val isoCode: String,
    // for GeoRegion.regionType
    @Json(name = "official_name_loc") val officialNameLoc: String,
    @Json(name = "official_name") val officialName: String,
    @Json(name = "official_status") val officialStatus: String,

    @Json(name = "geocodeArea") val geocodeArea: String,
    @Json(name = "locale") val locale: String,
    // for GeoRegion.regionName
    @Json(name = "name_loc") val nameLoc: String,
    @Json(name = "name") val name: String,
    @Json(name = "flag") val flag: String
)