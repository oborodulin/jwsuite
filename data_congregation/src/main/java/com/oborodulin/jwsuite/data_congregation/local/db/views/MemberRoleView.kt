package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity

@DatabaseView(
    viewName = MemberRoleView.VIEW_NAME,
    value = "SELECT mr.*, r.* FROM ${MemberRoleEntity.TABLE_NAME} mr JOIN ${RoleEntity.TABLE_NAME} r ON r.roleId = mr.mrRolesId"
)
class MemberRoleView(
    @Embedded val memberRole: MemberRoleEntity,
    @Embedded val role: RoleEntity
) {
    companion object {
        const val VIEW_NAME = "member_roles_view"
    }
}