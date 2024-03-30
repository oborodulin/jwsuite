package com.oborodulin.jwsuite.data_geo.local.db.entities.pojo

import androidx.room.Embedded
import kotlinx.serialization.Serializable

@Serializable
data class OsmData(
    val geocode: String? = null,
    val osmId: Long? = null,
    @Embedded val coordinates: Coordinates = Coordinates()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OsmData
        return osmId == other.osmId
    }

    override fun hashCode() = osmId.hashCode()
    override fun toString() = "OsmData(${geocode.orEmpty().plus(coordinates)}; osmId = $osmId)"
}