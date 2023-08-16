package com.oborodulin.jwsuite.data_geo.local.db.entities.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity

data class MicrodistrictWithStreets(
    @Embedded
    val district: GeoMicrodistrictEntity,
    @Relation(
        parentColumn = "microdistrictId",
        entityColumn = "streetId",
        associateBy = Junction(
            GeoDistrictStreetEntity::class,
            parentColumn = "dsMicrodistrictsId",
            entityColumn = "dsStreetsId"
        )
    )
    val streets: List<GeoStreetEntity> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MicrodistrictWithStreets
        if (district.id() != other.district.id() || district.key() != other.district.key()) return false

        return true
    }

    override fun hashCode() = district.hashCode()
}
