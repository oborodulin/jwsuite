package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME

@DatabaseView(
    viewName = MemberActualRoleView.VIEW_NAME,
    value = """
    SELECT mr.*, r.* FROM ${MemberRoleEntity.TABLE_NAME} mr JOIN ${RoleEntity.TABLE_NAME} r ON r.roleId = mr.mrRolesId
    WHERE ifnull(strftime($DB_FRACT_SEC_TIME, mr.roleExpiredDate), strftime($DB_FRACT_SEC_TIME, 'now', 'localtime')) >= strftime($DB_FRACT_SEC_TIME, 'now', 'localtime')
"""
)
class MemberActualRoleView(
    @Embedded val memberRole: MemberRoleEntity,
    @Embedded val role: RoleEntity
) {
    companion object {
        const val VIEW_NAME = "member_actual_roles_view"
    }
}