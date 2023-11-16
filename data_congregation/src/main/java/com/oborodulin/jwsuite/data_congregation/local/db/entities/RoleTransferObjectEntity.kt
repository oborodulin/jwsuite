package com.oborodulin.jwsuite.data_congregation.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = RoleTransferObjectEntity.TABLE_NAME,
    indices = [Index(value = ["rtoRolesId", "rtoTransferObjectsId"], unique = true)],
    foreignKeys = [ForeignKey(
        entity = RoleEntity::class,
        parentColumns = arrayOf("roleId"),
        childColumns = arrayOf("rtoRolesId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    ), ForeignKey(
        entity = TransferObjectEntity::class,
        parentColumns = arrayOf("transferObjectId"),
        childColumns = arrayOf("rtoTransferObjectsId"),
        onDelete = ForeignKey.CASCADE,
        deferred = true
    )]
)
@Serializable
data class RoleTransferObjectEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val roleTransferObjectId: UUID = UUID.randomUUID(),
    val isPersonalData: Boolean,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val rtoRolesId: UUID,
    @Serializable(with = UUIDSerializer::class)
    @ColumnInfo(index = true) val rtoTransferObjectsId: UUID
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "role_transfer_objects"

        fun defaultRoleTransferObject(
            roleId: UUID, transferObjectId: UUID, isPersonalData: Boolean
        ) = RoleTransferObjectEntity(
            rtoRolesId = roleId, rtoTransferObjectsId = transferObjectId,
            isPersonalData = isPersonalData
        )
    }

    override fun id() = this.roleTransferObjectId

    override fun key(): Int {
        var result = rtoTransferObjectsId.hashCode()
        result = result * 31 + rtoRolesId.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Role Transfer Object Entity")
            .append(" [rtoRolesId = ").append(rtoRolesId)
            .append("; rtoTransferObjectsId = ").append(rtoTransferObjectsId)
            .append("] roleTransferObjectId = ").append(roleTransferObjectId)
        return str.toString()
    }
}