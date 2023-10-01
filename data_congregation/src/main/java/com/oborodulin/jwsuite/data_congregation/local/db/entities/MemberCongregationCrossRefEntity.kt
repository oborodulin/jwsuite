package com.oborodulin.jwsuite.data_congregation.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.OffsetDateTimeSerializer
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(
    tableName = MemberCongregationCrossRefEntity.TABLE_NAME,
    indices = [Index(
        value = ["mcCongregationsId", "mcMembersId", "activityDate"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = MemberEntity::class,
        parentColumns = arrayOf("memberId"),
        childColumns = arrayOf("mcMembersId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = CongregationEntity::class,
        parentColumns = arrayOf("congregationId"),
        childColumns = arrayOf("mcCongregationsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class MemberCongregationCrossRefEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val memberCongregationId: UUID = UUID.randomUUID(),
    @Serializable(with = OffsetDateTimeSerializer::class)
    val activityDate: OffsetDateTime = OffsetDateTime.now(),
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val mcMembersId: UUID,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val mcCongregationsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "member_congregations"

        fun defaultCongregationMember(
            congregationId: UUID, memberId: UUID,
            activityDate: OffsetDateTime = OffsetDateTime.now()
        ) = MemberCongregationCrossRefEntity(
            mcCongregationsId = congregationId, mcMembersId = memberId,
            activityDate = activityDate
        )
    }

    override fun id() = this.memberCongregationId

    override fun key(): Int {
        var result = mcCongregationsId.hashCode()
        result = result * 31 + mcMembersId.hashCode()
        result = result * 31 + activityDate.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Member Congregation Entity").append(" from ")
            .append(DateTimeFormatter.ISO_LOCAL_DATE.format(activityDate))
            .append(" [cmCongregationsId = ").append(mcCongregationsId)
            .append("; cmMembersId = ").append(mcMembersId)
            .append("] memberCongregationId = ").append(memberCongregationId)
        return str.toString()
    }
}