package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity

@DatabaseView(
    viewName = MemberRoleTransferObjectView.VIEW_NAME,
    value = """
SELECT mrv.*, rto.*, o.* 
FROM ${MemberRoleView.VIEW_NAME} mrv JOIN ${RoleTransferObjectEntity.TABLE_NAME} rto ON rto.rtoRolesId = mrv.roleId
    JOIN ${TransferObjectEntity.TABLE_NAME} o ON o.transferObjectId = rto.rtoTransferObjectsId
"""
)
class MemberRoleTransferObjectView(
    @Embedded val memberRole: MemberRoleView,
    @Embedded val roleTransferObject: RoleTransferObjectEntity,
    @Embedded val transferObject: TransferObjectEntity
) {
    companion object {
        const val VIEW_NAME = "member_role_transfer_objects_view"
    }
}