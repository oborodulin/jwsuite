package com.oborodulin.jwsuite.data_geo.remote.osm.model.localitydistrict

import com.oborodulin.jwsuite.data_geo.remote.osm.model.Geometry
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Osm3s
import com.oborodulin.jwsuite.domain.util.Constants.OSM_TIMEOUT
import com.squareup.moshi.Json
import java.util.Locale
import java.util.UUID

data class LocalityDistrictApiModel(
    @Json(name = "version") val version: String,
    @Json(name = "generator") val generator: String,
    @Json(name = "osm3s") val osm3s: Osm3s,
    @Json(name = "elements") val elements: List<LocalityDistrictElement>
) {
    companion object {
        // https://stackoverflow.com/questions/30690724/dollar-sign-character-in-multiline-strings
        fun data(
            localityId: UUID,
            geocodeArea: String,
            incLocalityDistrictType: String, // район
            locale: String? = Locale.getDefault().language.substringBefore('-')
        ) = """
    [out:json][timeout:$OSM_TIMEOUT];
    {{geocodeArea:$geocodeArea}}->.searchArea;
    (
        node[place="borough"]["name"~"."](area.searchArea);
        rel["admin_level"~"^[5-8]${'$'}"]["name"~"район", i](area.searchArea);
        rel["admin_level"~"^[5-8]${'$'}"][border_type="county"]["name"~"."](area.searchArea);
    )->.crl;
    foreach.crl(
        convert LocalityDistrictType 
            osmType = type(), ::id = id(), ::geom = geom(), localityId = "$localityId", wikidata = t["wikidata"], gnis_id = t["gnis:feature_id"], geocodeArea = t["name:en"], locale = "$locale", name_loc = t["name:$locale"], name = t["name"];
        out center;
    );
    """.trimIndent()
    }
}

data class LocalityDistrictElement(
    @Json(name = "type") val type: String,
    @Json(name = "id") val id: Long,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "tags") val tags: LocalityDistrictTags
)

data class LocalityDistrictTags(
    @Json(name = "osmType") val osmType: String,
    @Json(name = "localityId") val localityId: UUID,
    // GeoRegionDistrict.districtShortName
    @Json(name = "wikidata") val wikidata: String,

    @Json(name = "geocodeArea") val geocodeArea: String,
    @Json(name = "locale") val locale: String,
    // GeoRegionDistrict.districtName
    @Json(name = "name_loc") val nameLoc: String,
    @Json(name = "name") val name: String,
    @Json(name = "flag") val flag: String
)