package com.oborodulin.jwsuite.data_geo.remote.osm.model.regiondistrict

import com.oborodulin.jwsuite.data_geo.remote.osm.model.Geometry
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Osm3s
import com.squareup.moshi.Json
import java.util.Locale
import java.util.UUID

data class RegionDistrictApiModel(
    @Json(name = "version") val version: String,
    @Json(name = "generator") val generator: String,
    @Json(name = "osm3s") val osm3s: Osm3s,
    @Json(name = "elements") val elements: List<RegionDistrictElement>
) {
    companion object {
        fun data(
            regionId: UUID,
            geocodeArea: String,
            locale: String? = Locale.getDefault().language.substringBefore('-')
        ) = """
    [out:json][timeout:25];
    {{geocodeArea:$geocodeArea}}->.searchArea;
    (rel["admin_level"="6"][place="district"](area.searchArea);)->.rdr;
    foreach.rdr(
        convert RegionDistrictType 
            osmType = type(), ::id = id(), ::geom = geom(), regionId = "$regionId", wikidata = t["wikidata"], geocodeArea = t["name:en"], locale = "$locale", name = t["name:$locale"], flag = t["flag"];
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

data class RegionDistrictTags(
    @Json(name = "osmType") val osmType: String,
    @Json(name = "regionId") val regionId: UUID,
    @Json(name = "wikidata") val wikidata: String,
    @Json(name = "geocodeArea") val geocodeArea: String,
    @Json(name = "locale") val locale: String,
    @Json(name = "name") val name: String,
    @Json(name = "flag") val flag: String
)