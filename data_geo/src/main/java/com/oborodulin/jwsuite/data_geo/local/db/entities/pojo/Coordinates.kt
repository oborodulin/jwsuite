package com.oborodulin.jwsuite.data_geo.local.db.entities.pojo

import com.oborodulin.home.common.data.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Coordinates(
    @Serializable(with = BigDecimalSerializer::class)
    val latitude: BigDecimal? = null,
    @Serializable(with = BigDecimalSerializer::class)
    val longitude: BigDecimal? = null
) {
    companion object {
        fun fromLatAndLon(lat: BigDecimal?, lon: BigDecimal?) =
            Coordinates(latitude = lat, longitude = lon)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coordinates
        val result = other.latitude?.let { latitude?.compareTo(it) == 0 } ?: false
        return result && other.longitude?.let { longitude?.compareTo(other.longitude) == 0 } ?: false
    }

    override fun hashCode(): Int {
        val result = latitude.hashCode()
        return result * 31 + longitude.hashCode()
    }
}
