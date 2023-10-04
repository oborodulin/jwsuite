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
import java.util.UUID

@Entity(
    tableName = MemberRoleEntity.TABLE_NAME,
    indices = [Index(
        value = ["mrMembersId", "mrRolesId"],
        unique = true
    )],
    foreignKeys = [ForeignKey(
        entity = MemberEntity::class,
        parentColumns = arrayOf("memberId"),
        childColumns = arrayOf("mrMembersId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = RoleEntity::class,
        parentColumns = arrayOf("roleId"),
        childColumns = arrayOf("mrRolesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class MemberRoleEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val memberRoleId: UUID = UUID.randomUUID(),
    @Serializable(with = OffsetDateTimeSerializer::class)
    val roleExpiredDate: OffsetDateTime? = null,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val mrMembersId: UUID,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val mrRolesId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "member_roles"

        fun defaultMemberRole(memberId: UUID, roleId: UUID) = MemberRoleEntity(
            mrMembersId = memberId, mrRolesId = roleId
        )
    }

    override fun id() = this.memberRoleId

    override fun key(): Int {
        var result = mrRolesId.hashCode()
        result = result * 31 + mrMembersId.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Member Role Entity")
            .append(" [mrMembersId = ").append(mrMembersId)
            .append("; mrRolesId = ").append(mrRolesId)
            .append("] memberRoleId = ").append(memberRoleId)
        return str.toString()
    }
}