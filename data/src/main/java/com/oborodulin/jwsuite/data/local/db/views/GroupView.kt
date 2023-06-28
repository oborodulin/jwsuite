package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GroupEntity

@DatabaseView(
    viewName = GroupView.VIEW_NAME,
    value = """
SELECT g.*, c.* FROM ${GroupEntity.TABLE_NAME} g JOIN ${CongregationView.VIEW_NAME} c ON c.congregationId = g.gCongregationsId
"""
)
class GroupView(
    @Embedded
    val group: GroupEntity,
    @Embedded
    val congregation: CongregationView,
) {
    companion object {
        const val VIEW_NAME = "groups_view"
    }
}