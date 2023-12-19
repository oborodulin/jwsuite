package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity

@DatabaseView(
    viewName = RoleTransferObjectView.VIEW_NAME,
    value = "SELECT rto.*, o.* FROM ${RoleTransferObjectEntity.TABLE_NAME} rto JOIN ${TransferObjectEntity.TABLE_NAME} o ON o.transferObjectId = rto.rtoTransferObjectsId"
)
class RoleTransferObjectView(
    @Embedded val roleTransferObject: RoleTransferObjectEntity,
    @Embedded val transferObject: TransferObjectEntity
) {
    companion object {
        const val VIEW_NAME = "role_transfer_objects_view"
    }
}