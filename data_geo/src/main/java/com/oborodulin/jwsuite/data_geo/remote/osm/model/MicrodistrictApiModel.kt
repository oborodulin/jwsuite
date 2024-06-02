package com.oborodulin.jwsuite.data_geo.remote.osm.model

import com.oborodulin.jwsuite.domain.util.Constants.OSM_TIMEOUT
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Locale
import java.util.UUID

data class MicrodistrictApiModel(
    @Json(name = "version") val version: String,
    @Json(name = "generator") val generator: String,
    @Json(name = "osm3s") val osm3s: Osm3s,
    @Json(name = "elements") val elements: List<MicrodistrictElement>
) {
    companion object {
        // https://stackoverflow.com/questions/30690724/dollar-sign-character-in-multiline-strings
        //Budyonny Raion
        //Nevsky District
        //Kuntsevo District
        //Kings County
        //Queens County
        fun data(
            localityId: UUID,
            localityDistrictId: UUID? = null,
            geocodeArea: String,
            locale: String? = Locale.getDefault().language
        ) = """
[out:json][timeout:$OSM_TIMEOUT];
(area${localityDistrictId?.let { "[admin_level~\"^[5-9]|10${'$'}\"]" } ?: "[place~\"^(city|town|village|hamlet|isolated_dwelling)${'$'}\"]"}["name:en"="$geocodeArea"];)->.searchArea;
(
    node[place~"^(suburb|quarter|neighbourhood|city_block|plot)${'$'}"]["name"](area.searchArea);
    rel["admin_level"~"^[8,9]|10${'$'}"][place="suburb"]["name"](area.searchArea);
)->.crl;
foreach.crl(
    convert MicrodistrictType 
        osmType = type(), ::id = id(), ::geom = geom(), localityId = "$localityId", localityDistrictId = "${
            localityDistrictId?.toString().orEmpty()
        }", wikidata = t["wikidata"], gnis_id = t["gnis:feature_id"], place = t["place"], geocodeArea = t["name:en"], locale = "$locale", name_loc = t["name:$locale"], name = t["name"];
    out center;
);
    """.trimIndent()
    }
}

data class MicrodistrictElement(
    @Json(name = "type") val type: String,
    @Json(name = "id") val id: Long,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "tags") val tags: MicrodistrictTags
)

@JsonClass(generateAdapter = true)
data class MicrodistrictTags(
    @Json(name = "osmType") val osmType: String,
    @Json(name = "localityId") val localityId: UUID,
    @Json(name = "localityDistrictId") val localityDistrictId: UUID?,
    // GeoMicrodistrict.microdistrictShortName
    @Json(name = "wikidata") val wikidata: String,
    @Json(name = "gnis_id") val gnisId: String,
    // GeoMicrodistrict.microdistrictType
    @Json(name = "place") val place: String,

    @Json(name = "geocodeArea") val geocodeArea: String,
    @Json(name = "locale") val locale: String,
    // GeoMicrodistrict.microdistrictName
    @Json(name = "name_loc") val nameLoc: String,
    @Json(name = "name") val name: String
)