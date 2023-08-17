package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity

@DatabaseView(
    viewName = MemberView.VIEW_NAME,
    value = """
SELECT m.*, g.* FROM ${MemberEntity.TABLE_NAME} m JOIN ${GroupView.VIEW_NAME} g ON g.groupId = m.mGroupsId
"""
)
class MemberView(
    @Embedded val member: MemberEntity,
    @Embedded val group: GroupView
) {
    companion object {
        const val VIEW_NAME = "members_view"
    }
}