package com.oborodulin.jwsuite.data_territory.remote.osm.model.house

import com.oborodulin.jwsuite.data_geo.remote.osm.model.Geometry
import com.oborodulin.jwsuite.data_geo.remote.osm.model.Osm3s
import com.oborodulin.jwsuite.domain.util.Constants.OSM_TIMEOUT
import com.squareup.moshi.Json
import java.util.UUID

data class HouseApiModel(
    @Json(name = "version") val version: String,
    @Json(name = "generator") val generator: String,
    @Json(name = "osm3s") val osm3s: Osm3s,
    @Json(name = "elements") val elements: List<HouseElement>
) {
    companion object {
        // Donetsk / Budyonny Raion
        // Карагандинська вулиця
        // 230-ї Стрілецької Дивізії вулиця
        fun data(
            streetId: UUID,
            localityDistrictId: UUID? = null,
            geocodeArea: String,
            streetName: String
        ) = """
    [out:json][timeout:$OSM_TIMEOUT];
    {{geocodeArea:$geocodeArea}}->.searchArea;
    nwr["building"]["addr:street"="$streetName"](area.searchArea)->.chs;
    foreach.chs(
        convert HouseType 
            osmType = type(), ::id = id(), ::geom = geom(), streetId = "$streetId", localityDistrictId = "${
            localityDistrictId?.toString().orEmpty()
        }", building = t["building"], housenumber = t["addr:housenumber"], postcode = t["addr:postcode"], flats  = t["building:flats"], levels = t["building:levels"], max_level = t["max_level"];
        out center;
    );
    """.trimIndent()
    }
}

data class HouseElement(
    @Json(name = "type") val type: String,
    @Json(name = "id") val id: Long,
    @Json(name = "geometry") val geometry: Geometry,
    @Json(name = "tags") val tags: HouseTags
)

data class HouseTags(
    @Json(name = "osmType") val osmType: String,
    @Json(name = "streetId") val streetId: UUID,
    @Json(name = "localityDistrictId") val localityDistrictId: UUID?,
    @Json(name = "building") val building: String,
    @Json(name = "housenumber") val houseNumber: String,
    @Json(name = "postcode") val postcode: String,
    @Json(name = "flats") val flats: String,
    @Json(name = "levels") val levels: String,
    @Json(name = "max_level") val maxLevel: String
)