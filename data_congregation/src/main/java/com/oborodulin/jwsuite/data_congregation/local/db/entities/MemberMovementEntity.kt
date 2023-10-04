package com.oborodulin.jwsuite.data_congregation.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.OffsetDateTimeSerializer
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.domain.util.MemberType
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(
    tableName = MemberMovementEntity.TABLE_NAME,
    indices = [Index(
        value = ["mMembersId", "memberType", "movementDate"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = MemberEntity::class,
        parentColumns = arrayOf("memberId"),
        childColumns = arrayOf("mMembersId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class MemberMovementEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val memberMovementId: UUID = UUID.randomUUID(),
    val memberType: MemberType = MemberType.PREACHER,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val movementDate: OffsetDateTime = OffsetDateTime.now(),
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val mMembersId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "member_movements"

        fun defaultMemberMovement(
            memberMovementId: UUID = UUID.randomUUID(), memberId: UUID = UUID.randomUUID(),
            memberType: MemberType = MemberType.PREACHER,
            movementDate: OffsetDateTime = OffsetDateTime.now()
        ) = MemberMovementEntity(
            memberMovementId = memberMovementId, mMembersId = memberId,
            memberType = memberType, movementDate = movementDate
        )
    }

    override fun id() = this.memberMovementId

    override fun key() = this.memberMovementId.hashCode()

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Member Movement Entity '").append(memberType).append("' ").append(" from ")
            .append(DateTimeFormatter.ISO_LOCAL_DATE.format(movementDate))
            .append(" memberMovementId = ").append(memberMovementId)
        return str.toString()
    }
}