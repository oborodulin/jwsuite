package com.oborodulin.jwsuite.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.entities.BaseEntity
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(
    tableName = CongregationMemberCrossRefEntity.TABLE_NAME,
    indices = [Index(
        value = ["cmCongregationsId", "cmMembersId", "activityDate"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = CongregationEntity::class,
        parentColumns = arrayOf("congregationId"),
        childColumns = arrayOf("cmCongregationsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = MemberEntity::class,
        parentColumns = arrayOf("memberId"),
        childColumns = arrayOf("cmMembersId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class CongregationMemberCrossRefEntity(
    @PrimaryKey val congregationMemberId: UUID = UUID.randomUUID(),
    val activityDate: OffsetDateTime = OffsetDateTime.now(),
    @ColumnInfo(index = true) val cmCongregationsId: UUID,
    @ColumnInfo(index = true) val cmMembersId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "congregations_members"

        fun defaultCongregationMember(
            congregationId: UUID, memberId: UUID,
            activityDate: OffsetDateTime = OffsetDateTime.now()
        ) = CongregationMemberCrossRefEntity(
            cmCongregationsId = congregationId, cmMembersId = memberId,
            activityDate = activityDate
        )
    }

    override fun id() = this.congregationMemberId

    override fun key(): Int {
        var result = cmCongregationsId.hashCode()
        result = result * 31 + cmMembersId.hashCode()
        result = result * 31 + activityDate.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Territory Member Entity").append(" from ")
            .append(DateTimeFormatter.ISO_LOCAL_DATE.format(activityDate))
            .append(" [cmCongregationsId = ").append(cmCongregationsId)
            .append("; cmMembersId = ").append(cmMembersId)
            .append("] congregationMemberId = ").append(congregationMemberId)
        return str.toString()
    }
}