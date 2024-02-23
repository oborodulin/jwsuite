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
            locale: String? = Locale.getDefault().language
        ) = """
    [out:json][timeout:25];
    {{geocodeArea:$geocodeArea}}->.searchArea;
    (rel["admin_level"="4"](area.searchArea);)->.rr;
    (node(r.rr)[place="state"];)->.nr;
    foreach.nr(
        convert RegionType 
            osmType = type(), ::id = id(), ::geom = geom(), countryId = "$countryId" ref = t["ref"], isoCode = t["ISO3166-2"], geocodeArea = t["name:en"], locale = "$locale", name = t["name:$locale"], flag = t["flag"];
	    out geom;
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
    @Json(name = "ref") val ref: String,
    @Json(name = "isoCode") val isoCode: String,
    @Json(name = "geocodeArea") val geocodeArea: String,
    @Json(name = "locale") val locale: String,
    @Json(name = "name") val name: String,
    @Json(name = "flag") val flag: String
)