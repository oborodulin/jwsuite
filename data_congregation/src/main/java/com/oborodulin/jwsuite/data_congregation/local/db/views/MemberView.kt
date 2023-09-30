package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME

@DatabaseView(
    viewName = MemberView.VIEW_NAME,
    value = """
SELECT m.*, mm.*, gv.* FROM ${MemberEntity.TABLE_NAME} m JOIN ${GroupView.VIEW_NAME} gv ON gv.groupId = m.mGroupsId
     JOIN (SELECT mMembersId, MAX(strftime(${DB_FRACT_SEC_TIME}, movementDate)) AS maxMovementDate 
            FROM ${MemberMovementEntity.TABLE_NAME} GROUP BY mMembersId ) mm 
        ON mm.mMembersId = m.memberId
"""
)
class MemberView(
    @Embedded val member: MemberEntity,
    @Embedded val movement: MemberMovementEntity,
    @Embedded val group: GroupView
) {
    companion object {
        const val VIEW_NAME = "members_view"
    }
}