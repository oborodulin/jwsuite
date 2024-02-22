package com.oborodulin.jwsuite.data_geo.remote.osm.model

import com.squareup.moshi.Json
import java.math.BigDecimal

data class Geometry(
    @Json(name = "type") val type: String,
    @Json(name = "coordinates") val coordinates: List<BigDecimal>
)