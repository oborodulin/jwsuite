package com.oborodulin.jwsuite.data.local.db.entities.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.oborodulin.jwsuite.data.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryMemberCrossRefEntity

data class TerritoryWithMembers(
    @Embedded
    val territory: TerritoryEntity,
    @Relation(
        parentColumn = "territoryId",
        entityColumn = "memberId",
        associateBy = Junction(
            TerritoryMemberCrossRefEntity::class,
            parentColumn = "territoriesId",
            entityColumn = "membersId"
        )
    )
    val members: List<MemberEntity>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TerritoryWithMembers
        if (territory.id() != other.territory.id() || territory.key() != other.territory.key()) return false

        return true
    }

    override fun hashCode() = territory.hashCode()
}
