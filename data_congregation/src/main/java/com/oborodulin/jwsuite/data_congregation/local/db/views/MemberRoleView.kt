package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity

@DatabaseView(
    viewName = MemberRoleView.VIEW_NAME,
    value = "SELECT mr.*, r.* FROM ${MemberRoleCrossRefEntity.TABLE_NAME} mr JOIN ${RoleEntity.TABLE_NAME} r ON r.roleId = mr.mrRolesId"
)
class MemberRoleView(
    @Embedded val memberRole: MemberRoleCrossRefEntity,
    @Embedded val role: RoleEntity
) {
    companion object {
        const val VIEW_NAME = "member_roles_view"
    }
}