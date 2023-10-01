package com.oborodulin.jwsuite.data_congregation.local.db.entities

import android.content.Context
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.data_congregation.R
import com.oborodulin.jwsuite.domain.util.MemberRoleType
import kotlinx.serialization.Serializable
import java.util.UUID

@Entity(
    tableName = RoleEntity.TABLE_NAME,
    indices = [Index(value = ["roleType"], unique = true)]
)
@Serializable
data class RoleEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val roleId: UUID = UUID.randomUUID(),
    val roleType: MemberRoleType = MemberRoleType.USER,
    val roleName: String
) : BaseEntity() {

    companion object {
        const val TABLE_NAME = "roles"

        fun defaultRole(
            roleId: UUID = UUID.randomUUID(),
            roleType: MemberRoleType = MemberRoleType.USER, roleName: String
        ) = RoleEntity(
            roleId = roleId, roleType = roleType, roleName = roleName
        )

        fun adminRole(ctx: Context) = defaultRole(
            roleType = MemberRoleType.ADMIN,
            roleName = ctx.resources.getString(R.string.def_role_name_admin)
        )

        fun userRole(ctx: Context) = defaultRole(
            roleType = MemberRoleType.USER,
            roleName = ctx.resources.getString(R.string.def_role_name_user)
        )

        fun territoriesRole(ctx: Context) = defaultRole(
            roleType = MemberRoleType.TERRITORIES,
            roleName = ctx.resources.getString(R.string.def_role_name_territories)
        )

        fun billsRole(ctx: Context) = defaultRole(
            roleType = MemberRoleType.BILLS,
            roleName = ctx.resources.getString(R.string.def_role_name_bills)
        )

        fun reportsRole(ctx: Context) = defaultRole(
            roleType = MemberRoleType.REPORTS,
            roleName = ctx.resources.getString(R.string.def_role_name_reports)
        )
    }

    override fun id() = this.roleId
    override fun key() = this.roleType.hashCode()
    override fun toString(): String {
        val str = StringBuffer()
        str.append("Role Entity '").append(roleName).append("' ").append(roleName)
            .append(" roleId = ").append(roleId)
        return str.toString()
    }
}