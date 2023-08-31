package com.oborodulin.jwsuite.data_territory.local.db.entities.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity

data class CongregationWithTerritories(
    @Embedded
    val congregation: CongregationEntity,
    @Relation(
        parentColumn = "congregationId",
        entityColumn = "territoryId",
        associateBy = Junction(
            com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity::class,
            parentColumn = "ctCongregationsId",
            entityColumn = "ctTerritoriesId"
        )
    )
    val territories: List<TerritoryEntity>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CongregationWithTerritories
        if (congregation.id() != other.congregation.id() || congregation.key() != other.congregation.key()) return false

        return true
    }

    override fun hashCode() = congregation.hashCode()
}
