package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME

@DatabaseView(
    viewName = MemberMovementView.VIEW_NAME,
    value = """
SELECT m.*, mm.*, g.* FROM ${MemberEntity.TABLE_NAME} m JOIN ${GroupEntity.TABLE_NAME} g ON g.groupId = m.mGroupsId
     JOIN ${MemberMovementEntity.TABLE_NAME} mm ON mm.mMembersId = m.memberId
"""
)
class MemberMovementView(
    @Embedded val member: MemberEntity,
    @Embedded val movement: MemberMovementEntity,
    @Embedded val group: GroupEntity
) {
    companion object {
        const val VIEW_NAME = "member_movements_view"
    }
}