package com.oborodulin.jwsuite.data_geo.remote.osm.model.street

import com.oborodulin.jwsuite.data_geo.remote.osm.model.Geometry
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Osm3s
import com.oborodulin.jwsuite.domain.util.Constants.OSM_TIMEOUT
import com.squareup.moshi.Json
import java.util.Locale
import java.util.UUID

data class StreetApiModel(
    @Json(name = "version") val version: String,
    @Json(name = "generator") val generator: String,
    @Json(name = "osm3s") val osm3s: Osm3s,
    @Json(name = "elements") val elements: List<StreetElement>
) {
    companion object {
        // https://help.openstreetmap.org/questions/87188/how-to-get-the-list-of-addresses-or-street-names-by-city
        //Donetsk / Budyonny Raion
        //Saint Petersburg
        //Moscow
        //New York
        // geocodeArea = t["name:en"],
        fun data(
            localityId: UUID,
            localityDistrictId: UUID? = null,
            geocodeArea: String,
            locale: String? = Locale.getDefault().language.substringBefore('-')
        ) = """
    [out:json][timeout:$OSM_TIMEOUT];
    {{geocodeArea:$geocodeArea}}->.searchArea;
    way["highway"]["name"](area.searchArea)->.crl;
    foreach.crl(
        convert StreetType
            osmType = type(), ::id = id(), ::geom = geom(), localityId = "$localityId", localityDistrictId = "${
            localityDistrictId?.toString().orEmpty()
        }", highway = t["highway"], locale = "$locale", name_loc = t["name:$locale"], name = t["name"];
        out center;
    );
    """.trimIndent()
    }
}

data class StreetElement(
    @Json(name = "type") val type: String,
    @Json(name = "id") val id: Long,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "tags") val tags: StreetTags
)

data class StreetTags(
    @Json(name = "osmType") val osmType: String,
    @Json(name = "localityId") val localityId: UUID,
    @Json(name = "localityDistrictId") val localityDistrictId: UUID?,
    @Json(name = "highway") val highway: String,
    //@Json(name = "geocodeArea") val geocodeArea: String,
    @Json(name = "locale") val locale: String,
    @Json(name = "name_loc") val nameLoc: String,
    @Json(name = "name") val name: String
)