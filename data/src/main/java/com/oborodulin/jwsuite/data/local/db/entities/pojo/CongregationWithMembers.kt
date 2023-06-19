package com.oborodulin.jwsuite.data.local.db.entities.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.oborodulin.jwsuite.data.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data.local.db.entities.MemberEntity

data class CongregationWithMembers(
    @Embedded
    val congregation: CongregationEntity,
    @Relation(
        parentColumn = "congregationId",
        entityColumn = "memberId",
        associateBy = Junction(
            CongregationTerritoryCrossRefEntity::class,
            parentColumn = "congregationsId",
            entityColumn = "membersId"
        )
    )
    val members: List<MemberEntity>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CongregationWithMembers
        if (congregation.id() != other.congregation.id() || congregation.key() != other.congregation.key()) return false

        return true
    }

    override fun hashCode() = congregation.hashCode()
}
