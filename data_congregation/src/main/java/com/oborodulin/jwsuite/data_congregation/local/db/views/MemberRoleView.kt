package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME

@DatabaseView(
    viewName = MemberRoleView.VIEW_NAME,
    value = """
    SELECT mv.*, mr.*, r.* FROM ${MemberRoleEntity.TABLE_NAME} mr JOIN ${RoleEntity.TABLE_NAME} r ON r.roleId = mr.mrRolesId
                        JOIN ${MemberView.VIEW_NAME} mv ON mv.memberId = mr.mrMembersId
    WHERE ifnull(strftime($DB_FRACT_SEC_TIME, mr.roleExpiredDate), strftime($DB_FRACT_SEC_TIME, 'now', 'localtime')) >= strftime($DB_FRACT_SEC_TIME, 'now', 'localtime')
"""
)
class MemberRoleView(
    @Embedded val member: MemberView,
    @Embedded val memberRole: MemberRoleEntity,
    @Embedded val role: RoleEntity
) {
    companion object {
        const val VIEW_NAME = "member_roles_view"
    }
}