package com.oborodulin.jwsuite.data_congregation.local.db.entities.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity

data class GroupWithMembers(
    @Embedded
    val group: GroupEntity,
    @Relation(parentColumn = "groupId", entityColumn = "groupsId")
    val members: List<MemberEntity> = emptyList()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GroupWithMembers
        if (group.id() != other.group.id() || group.key() != other.group.key()) return false

        return true
    }

    override fun hashCode(): Int {
        return group.hashCode()
    }
}
