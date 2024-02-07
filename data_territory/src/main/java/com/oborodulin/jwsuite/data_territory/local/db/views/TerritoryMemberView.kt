package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity

@DatabaseView(
    viewName = TerritoryMemberView.VIEW_NAME,
    value = "SELECT tm.*, m.* FROM ${TerritoryMemberCrossRefEntity.TABLE_NAME} tm JOIN ${MemberEntity.TABLE_NAME} m ON tm.tmcMembersId = m.memberId"
)
class TerritoryMemberView(
    @Embedded val territoryMember: TerritoryMemberCrossRefEntity,
    @Embedded val member: MemberEntity
) {
    companion object {
        const val VIEW_NAME = "territory_members_view"
    }
}