package com.oborodulin.jwsuite.data.local.db.entities.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoStreetEntity

data class LocalityDistrictWithStreets(
    @Embedded
    val district: GeoLocalityDistrictEntity,
    @Relation(
        parentColumn = "localityDistrictId",
        entityColumn = "streetId",
        associateBy = Junction(
            GeoDistrictStreetEntity::class,
            parentColumn = "localityDistrictsId",
            entityColumn = "streetsId"
        )
    )
    val streets: List<GeoStreetEntity> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocalityDistrictWithStreets
        if (district.id() != other.district.id() || district.key() != other.district.key()) return false

        return true
    }

    override fun hashCode(): Int {
        return district.hashCode()
    }
}
