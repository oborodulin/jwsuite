package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded

@DatabaseView(
    viewName = MemberRoleView.VIEW_NAME,
    value = "SELECT mv.*, marv.* FROM ${MemberView.VIEW_NAME} mv JOIN ${MemberActualRoleView.VIEW_NAME} marv ON mv.memberId = marv.mrMembersId"
)
class MemberRoleView(
    @Embedded val member: MemberView,
    @Embedded val memberActualRole: MemberActualRoleView
) {
    companion object {
        const val VIEW_NAME = "member_roles_view"
    }
}