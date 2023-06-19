package com.oborodulin.jwsuite.data.local.db.entities.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.oborodulin.jwsuite.data.local.db.entities.CongregationEntity

data class CongregationWithGroupMembers(
    @Embedded
    val receipt: CongregationEntity,
    @Relation(parentColumn = "congregationId", entityColumn = "congregationsId")
    val groupMembers: List<GroupWithMembers> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CongregationWithGroupMembers
        if (receipt.id() != other.receipt.id() || receipt.key() != other.receipt.key()) return false

        return true
    }

    override fun hashCode(): Int {
        return receipt.hashCode()
    }
}
