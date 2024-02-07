package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.domain.util.Constants.MR_TERRITORIES_VAL

@DatabaseView(
    viewName = MemberServiceRoleView.VIEW_NAME,
    value = """
    SELECT m.*, marv.* FROM ${MemberEntity.TABLE_NAME} m JOIN ${MemberActualRoleView.VIEW_NAME} marv 
        ON m.memberId = marv.mrMembersId AND marv.roleType IN ($MR_TERRITORIES_VAL) 
"""
)
class MemberServiceRoleView(
    @Embedded val member: MemberEntity,
    @Embedded val memberServiceRole: MemberActualRoleView
) {
    companion object {
        const val VIEW_NAME = "member_service_roles_view"
    }
}