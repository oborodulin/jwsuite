package com.oborodulin.jwsuite.data_geo.remote.osm.model.region

import com.oborodulin.jwsuite.data_geo.remote.osm.model.Geometry
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Osm3s
import com.squareup.moshi.Json
import java.util.Locale
import java.util.UUID

data class RegionApiModel(
    @Json(name = "version") val version: String,
    @Json(name = "generator") val generator: String,
    @Json(name = "osm3s") val osm3s: Osm3s,
    @Json(name = "elements") val elements: List<RegionElement>
) {
    companion object {
        fun data(
            countryId: UUID,
            geocodeArea: String,
            locale: String? = Locale.getDefault().language.substringBefore('-')
        ) = """
    [out:json][timeout:25];
    {{geocodeArea:$geocodeArea}}->.searchArea;
    (rel["admin_level"="4"][~"^(ISO3166-2|addr:country|is_in:country_code)+${'$'}"~"^$locale(.)*${'$'}",i]["official_status"!~"город"]["name"~"."](area.searchArea);)->.rr;
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