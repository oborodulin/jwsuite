package com.oborodulin.jwsuite.data_geo.remote.osm.model.regiondistrict

import com.oborodulin.jwsuite.data_geo.remote.osm.model.Geometry
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Osm3s
import com.oborodulin.jwsuite.domain.util.Constants.OSM_TIMEOUT
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Locale
import java.util.UUID

data class RegionDistrictApiModel(
    @Json(name = "version") val version: String,
    @Json(name = "generator") val generator: String,
    @Json(name = "osm3s") val osm3s: Osm3s,
    @Json(name = "elements") val elements: List<RegionDistrictElement>
) {
    companion object {
        // https://stackoverflow.com/questions/30690724/dollar-sign-character-in-multiline-strings
        //Краснодарский край
        //Leningrad oblast
        //Donetsk Oblast
        //California
        fun data(
            regionId: UUID,
            geocodeArea: String,
            incRegionDistrictType: String, // район
            locale: String? = Locale.getDefault().language.substringBefore('-')
        ) = """
    [out:json][timeout:$OSM_TIMEOUT];
    {{geocodeArea:$geocodeArea}}->.searchArea;
    (
    rel["admin_level"="6"][~"^(place|official_status)*${'$'}"~"(district|$incRegionDistrictType)${'$'}"]["name"](area.searchArea);
    rel["admin_level"="6"][~"^county.*${'$'}"~"."]["border_type"!~"city"]["name"](area.searchArea);
    )->.rdr;
    foreach.rdr(
        convert RegionDistrictType 
            osmType = type(), ::id = id(), ::geom = geom(), regionId = "$regionId", county_abbrev = t["county:abbrev"], wikidata = t["wikidata"], geocodeArea = t["name:en"], locale = "$locale", name_loc = t["name:$locale"], name = t["name"], flag = t["flag"];
        out center;
    );
    """.trimIndent()
    }
}

data class RegionDistrictElement(
    @Json(name = "type") val type: String,
    @Json(name = "id") val id: Long,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "tags") val tags: RegionDistrictTags
)

@JsonClass(generateAdapter = true)
data class RegionDistrictTags(
    @Json(name = "osmType") val osmType: String,
    @Json(name = "regionId") val regionId: UUID,
    // GeoRegionDistrict.districtShortName
    @Json(name = "county_abbrev") val countyAbbrev: String,
    @Json(name = "wikidata") val wikidata: String,

    @Json(name = "geocodeArea") val geocodeArea: String,
    @Json(name = "locale") val locale: String,
    // GeoRegionDistrict.districtName
    @Json(name = "name_loc") val nameLoc: String,
    @Json(name = "name") val name: String,
    @Json(name = "flag") val flag: String
)