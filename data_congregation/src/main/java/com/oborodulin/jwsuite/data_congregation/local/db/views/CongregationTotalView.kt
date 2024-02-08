package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity

@DatabaseView(
    viewName = CongregationTotalView.VIEW_NAME,
    value = """
    SELECT c.*, ct.sumTotalGroups AS totalGroups, ct.sumTotalMembers AS totalMembers, ct.sumTotalFulltimeMembers AS totalFulltimeMembers,
        ctd.totalGroups AS diffGroups, ctd.totalMembers AS diffTerritoryIssued, ctd.totalFulltimeMembers AS diffFulltimeMembers  
    FROM ${FavoriteCongregationView.VIEW_NAME} c 
        JOIN (SELECT ctlCongregationsId, SUM(totalGroups) AS sumTotalGroups, SUM(totalMembers) AS sumTotalMembers, SUM(totalFulltimeMembers) AS sumTotalFulltimeMembers
                FROM ${CongregationTotalEntity.TABLE_NAME}
                GROUP BY ctlCongregationsId
            ) ct ON ct.ctlCongregationsId = c.congregationId
    JOIN ${CongregationTotalEntity.TABLE_NAME} ctd ON ctd.ctlCongregationsId = c.congregationId AND ctd.lastVisitDate IS NULL
"""
)
class CongregationTotalView(
    @Embedded val congregation: FavoriteCongregationView,
    val totalGroups: Int,
    val totalMembers: Int,
    val totalFulltimeMembers: Int,
    val diffGroups: Int,
    val diffMembers: Int,
    val diffFulltimeMembers: Int
) {
    companion object {
        const val VIEW_NAME = "congregation_totals_view"
    }
}
/*
SELECT c.*,
    (SELECT COUNT(mMembersId) AS cnt FROM ${MemberMovementEntity.TABLE_NAME} WHERE mMembersId = m.memberId AND memberType NOT IN ($MT_IN_ACTIVE_VAL)
        GROUP BY mMembersId) AS totalMembers,
    (SELECT COUNT(mMembersId) AS cnt FROM ${MemberMovementEntity.TABLE_NAME}
        WHERE mMembersId = m.memberId
            AND memberType NOT IN ($MT_IN_ACTIVE_VAL)
            AND strftime($DB_FRACT_SEC_TIME, movementDate) < ifnull(strftime($DB_FRACT_SEC_TIME, c.lastVisitDate), strftime($DB_FRACT_SEC_TIME, 'now', 'localtime'))
        GROUP BY mMembersId) AS prevMemberTotals,
    (SELECT COUNT(mMembersId) AS cnt FROM ${MemberMovementEntity.TABLE_NAME} WHERE mMembersId = m.memberId AND memberType IN ($MT_FULL_TIME_VAL)
        GROUP BY mMembersId) AS totalFulltimeMembers,
    (SELECT COUNT(mMembersId) AS cnt FROM ${MemberMovementEntity.TABLE_NAME}
        WHERE mMembersId = m.memberId
            AND memberType IN ($MT_FULL_TIME_VAL)
            AND strftime($DB_FRACT_SEC_TIME, movementDate) < ifnull(strftime($DB_FRACT_SEC_TIME, c.lastVisitDate), strftime($DB_FRACT_SEC_TIME, 'now', 'localtime'))
        GROUP BY mMembersId) AS prevFulltimeMemberTotals
FROM ${FavoriteCongregationView.VIEW_NAME} c JOIN ${GroupEntity.TABLE_NAME} g ON g.gCongregationsId = c.congregationId
    JOIN ${MemberEntity.TABLE_NAME} m ON m.mGroupsId = g.groupId
*/