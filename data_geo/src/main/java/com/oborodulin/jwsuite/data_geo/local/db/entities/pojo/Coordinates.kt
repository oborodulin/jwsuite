package com.oborodulin.jwsuite.data_geo.local.db.entities.pojo

import com.oborodulin.home.common.data.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Coordinates(
    @Serializable(with = BigDecimalSerializer::class)
    val latitude: BigDecimal,
    @Serializable(with = BigDecimalSerializer::class)
    val longitude: BigDecimal
) {
    companion object {
        fun fromLatAndLon(lat: BigDecimal?, lon: BigDecimal?) =
            lat?.let { latitude ->
                lon?.let { longitude -> Coordinates(latitude = latitude, longitude = longitude) }
            }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coordinates
        return !(latitude.compareTo(other.latitude) != 0 || longitude.compareTo(other.longitude) != 0)
    }

    override fun hashCode(): Int {
        val result = latitude.hashCode()
        return result * 31 + longitude.hashCode()
    }
}
