package com.oborodulin.jwsuite.data.local.db.entities.pojo

import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.CongregationEntity

data class CongregationWithGroupMembers(
    @Embedded
    val congregation: CongregationEntity,
    /*    @Relation(parentColumn = "congregationId", entityColumn = "gCongregationsId")
        val groupMembers: List<GroupWithMembers> = emptyList()

     */
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CongregationWithGroupMembers
        if (congregation.id() != other.congregation.id() || congregation.key() != other.congregation.key()) return false

        return true
    }

    override fun hashCode(): Int {
        return congregation.hashCode()
    }
}
