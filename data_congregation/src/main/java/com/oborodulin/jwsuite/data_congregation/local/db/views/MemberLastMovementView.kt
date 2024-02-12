package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME

@DatabaseView(
    viewName = MemberLastMovementView.VIEW_NAME,
    value = """
    SELECT mm.* FROM ${MemberMovementEntity.TABLE_NAME} mm
        JOIN (SELECT mMembersId, MAX(strftime($DB_FRACT_SEC_TIME, movementDate)) AS maxMovementDate FROM ${MemberMovementEntity.TABLE_NAME} GROUP BY mMembersId) mmx 
            ON mm.mMembersId = mmx.mMembersId AND strftime($DB_FRACT_SEC_TIME, mm.movementDate) = mmx.maxMovementDate
"""
)
class MemberLastMovementView(
    @Embedded val lastMemberMovement: MemberMovementEntity
) {
    companion object {
        const val VIEW_NAME = "member_last_movements_view"
    }
}