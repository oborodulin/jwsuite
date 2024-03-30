package com.oborodulin.jwsuite.data_geo.remote.osm.model.locality

import com.oborodulin.jwsuite.data_geo.remote.osm.model.Geometry
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Osm3s
import com.oborodulin.jwsuite.domain.util.Constants.OSM_TIMEOUT
import com.squareup.moshi.Json
import java.util.Locale
import java.util.UUID

data class LocalityApiModel(
    @Json(name = "version") val version: String,
    @Json(name = "generator") val generator: String,
    @Json(name = "osm3s") val osm3s: Osm3s,
    @Json(name = "elements") val elements: List<LocalityElement>
) {
    // https://help.openstreetmap.org/questions/19063/get-city-nodes-within-a-country-using-overpass-api
    // https://wiki.openstreetmap.org/wiki/RU:Key:admin_level
    // https://wiki.openstreetmap.org/wiki/Key:place
    // https://wiki.openstreetmap.org/wiki/Overpass_API/Overpass_QL#Key/value_matches_regular_expression_(~%22key_regex%22~%22value_regex%22)
    // https://wiki.openstreetmap.org/wiki/Key:gnis:feature_id
    //Kaarosta District
    //Volnovakha Raion
    //Mariupol Raion
    //Monterey County
    companion object {
        fun data(
            regionId: UUID,
            regionDistrictId: UUID? = null,
            geocodeArea: String,
            locale: String? = Locale.getDefault().language.substringBefore('-')
        ) = """ 
    [out:json][timeout:$OSM_TIMEOUT];
    {{geocodeArea:$geocodeArea}}->.searchArea;
    node[place~"^(city|town|village|hamlet|isolated_dwelling)${'$'}"]["name"~"."](area.searchArea)->.crl;
    foreach.crl(
        convert LocalityType 
            osmType = type(), ::id = id(), ::geom = geom(), regionId = "$regionId", regionDistrictId = "${
            regionDistrictId?.toString().orEmpty()
        }", postal_code = t["postal_code"], wikidata = t["wikidata"], gnis_id = t["gnis:feature_id"], place = t["place"], official_status = t["official_status"], prefix = t["name:prefix"], geocodeArea = t["name:en"], locale = "$locale", name_loc = t["name:$locale"], name = t["name"];
        out center;
    );
    """.trimIndent()
    }
}

data class LocalityElement(
    @Json(name = "type") val type: String,
    @Json(name = "id") val id: Long,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "tags") val tags: LocalityTags
)

data class LocalityTags(
    @Json(name = "osmType") val osmType: String,
    @Json(name = "regionId") val regionId: UUID,
    @Json(name = "regionDistrictId") val regionDistrictId: UUID?,
    // GeoLocality.localityCode
    @Json(name = "postal_code") val postalCode: String,
    @Json(name = "wikidata") val wikidata: String,
    @Json(name = "gnis_id") val gnisId: String,
    // GeoLocality.localityType
    @Json(name = "place") val place: String,
    @Json(name = "official_status") val officialStatus: String,
    @Json(name = "prefix") val prefix: String,

    @Json(name = "geocodeArea") val geocodeArea: String,
    @Json(name = "locale") val locale: String,
    // GeoLocality.localityName
    @Json(name = "name_loc") val nameLoc: String,
    @Json(name = "name") val name: String
)