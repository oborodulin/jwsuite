package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded

@DatabaseView(
    viewName = MemberRoleTransferObjectView.VIEW_NAME,
    value = "SELECT mrv.*, rtov.* FROM ${MemberRoleView.VIEW_NAME} mrv JOIN ${RoleTransferObjectView.VIEW_NAME} rtov ON rtov.rtoRolesId = mrv.roleId"
)
class MemberRoleTransferObjectView(
    @Embedded val memberRole: MemberRoleView,
    @Embedded val roleTransferObject: RoleTransferObjectView
) {
    companion object {
        const val VIEW_NAME = "member_role_transfer_objects_view"
    }
}