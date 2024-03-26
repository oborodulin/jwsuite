package com.oborodulin.jwsuite.data_geo.remote.osm.model.locality

import com.oborodulin.jwsuite.data_geo.remote.osm.model.Geometry
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Osm3s
import com.oborodulin.jwsuite.domain.util.Constants
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
    // https://wiki.openstreetmap.org/wiki/RU:Key:admin_level
    // https://wiki.openstreetmap.org/wiki/Key:place
    // https://wiki.openstreetmap.org/wiki/Overpass_API/Overpass_QL#Key/value_matches_regular_expression_(~%22key_regex%22~%22value_regex%22)
    companion object {
        fun data(
            regionId: UUID,
            geocodeArea: String,
            locale: String? = Locale.getDefault().language.substringBefore('-')
        ) = """
[out:json][timeout:25];
{{geocodeArea:Monterey County}}->.searchArea;
(
rel["admin_level"~"^([7-9]|10)${'$'}"]["name"~"."](area.searchArea);
//.rl out tags;
nr(r.rl)[place~"^(city|town|village|hamlet|isolated_dwelling)${'$'}"]["name"~"."];
)->.crl;
foreach.crl(
  convert LocalityType 
    osmType = type(), ::id = id(), ::geom = geom(), regionId = "regionId", place = t["place"], postal_code = t["postal_code"], prefix = t["name:prefix"], geocodeArea = t["name:en"], locale = "ru", name_loc = t["name:ru"], name = t["name"];
out center;
);            
    [out:json][timeout:$OSM_TIMEOUT];
    {{geocodeArea:$geocodeArea}}->.searchArea;
    (rel["admin_level"~"^([4-9]|10)${'$'}"](area.searchArea);)->.rl;
    (nr(r.rl)[place~"^(city|town|village|hamlet|isolated_dwelling)${'$'}"]["name:$locale"~"."];)->.crl;
    foreach.crl(
        convert LocalityType 
            osmType = type(), ::id = id(), ::geom = geom(), regionId = "$regionId", place = t["place"], postal_code = t["postal_code"], prefix = t["name:prefix"], geocodeArea = t["name:en"], locale = "$locale", name = t["name:$locale"];
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
    @Json(name = "place") val place: String,
    @Json(name = "postal_code") val postalCode: String,
    @Json(name = "prefix") val prefix: String,
    @Json(name = "geocodeArea") val geocodeArea: String,
    @Json(name = "locale") val locale: String,
    @Json(name = "name") val name: String
)