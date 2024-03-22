package com.oborodulin.jwsuite.data_geo.remote.osm.model

import com.squareup.moshi.Json
import java.time.OffsetDateTime

data class Osm3s(
    @Json(name = "timestamp_osm_base") val timestampOsmBase: OffsetDateTime,
    @Json(name = "timestamp_areas_base") val timestampAreasBase: OffsetDateTime,
    @Json(name = "copyright") val copyright: String
)