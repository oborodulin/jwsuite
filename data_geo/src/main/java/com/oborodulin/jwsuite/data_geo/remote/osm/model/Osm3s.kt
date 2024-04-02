package com.oborodulin.jwsuite.data_geo.remote.osm.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
data class Osm3s(
    @Json(name = "timestamp_osm_base") val timestampOsmBase: OffsetDateTime,
    @Json(name = "timestamp_areas_base") val timestampAreasBase: OffsetDateTime? = null,
    @Json(name = "copyright") val copyright: String
)