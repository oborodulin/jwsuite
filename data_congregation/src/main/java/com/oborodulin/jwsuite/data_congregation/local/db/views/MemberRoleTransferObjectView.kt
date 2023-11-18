package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity

@DatabaseView(
    viewName = MemberRoleTransferObjectView.VIEW_NAME,
    value = """
SELECT m.*, rto.*, o.* FROM ${MemberEntity.TABLE_NAME} m JOIN ${MemberRoleEntity.TABLE_NAME} mr ON mr.mrMembersId = m.memberId
     JOIN ${RoleTransferObjectEntity.TABLE_NAME} rto ON rto.rtoRolesId = mr.mrRolesId
     JOIN ${TransferObjectEntity.TABLE_NAME} o ON o.transferObjectId = rto.rtoTransferObjectsId
"""
)
class MemberRoleTransferObjectView(
    @Embedded val member: MemberEntity,
    @Embedded val roleTransferObject: RoleTransferObjectEntity,
    @Embedded val transferObject: TransferObjectEntity
) {
    companion object {
        const val VIEW_NAME = "member_role_transfer_objects_view"
    }
}