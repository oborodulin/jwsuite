package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME
import com.oborodulin.jwsuite.domain.util.Constants.MT_FULL_TIME_VAL
import com.oborodulin.jwsuite.domain.util.Constants.MT_IN_ACTIVE_VAL

@DatabaseView(
    viewName = CongregationTotalView.VIEW_NAME,
    value = """
SELECT c.*, 
    (SELECT COUNT(mMembersId) AS cnt FROM ${MemberMovementEntity.TABLE_NAME} WHERE mMembersId = m.memberId AND memberType NOT IN (${MT_IN_ACTIVE_VAL})
        GROUP BY mMembersId) AS totalMembers,  
    (SELECT COUNT(mMembersId) AS cnt FROM ${MemberMovementEntity.TABLE_NAME} 
        WHERE mMembersId = m.memberId
            AND memberType NOT IN (${MT_IN_ACTIVE_VAL})
            AND strftime(${DB_FRACT_SEC_TIME}, movementDate) < ifnull(strftime(${DB_FRACT_SEC_TIME}, c.lastVisitDate), strftime(${DB_FRACT_SEC_TIME}, 'now', 'localtime'))
        GROUP BY mMembersId) AS prevMemberTotals,
    (SELECT COUNT(mMembersId) AS cnt FROM ${MemberMovementEntity.TABLE_NAME} WHERE mMembersId = m.memberId AND memberType IN (${MT_FULL_TIME_VAL})
        GROUP BY mMembersId) AS totalFulltimeMembers,  
    (SELECT COUNT(mMembersId) AS cnt FROM ${MemberMovementEntity.TABLE_NAME} 
        WHERE mMembersId = m.memberId 
            AND memberType IN (${MT_FULL_TIME_VAL})
            AND strftime(${DB_FRACT_SEC_TIME}, movementDate) < ifnull(strftime(${DB_FRACT_SEC_TIME}, c.lastVisitDate), strftime(${DB_FRACT_SEC_TIME}, 'now', 'localtime'))
        GROUP BY mMembersId) AS prevFulltimeMemberTotals
FROM ${FavoriteCongregationView.VIEW_NAME} c JOIN ${GroupEntity.TABLE_NAME} g ON g.gCongregationsId = c.congregationId
    JOIN ${MemberEntity.TABLE_NAME} m ON m.mGroupsId = g.groupId
"""
)
class CongregationTotalView(
    @Embedded val congregation: FavoriteCongregationView,
    val totalMembers: Int,
    val prevMemberTotals: Int,
    val totalFulltimeMembers: Int,
    val prevFulltimeMemberTotals: Int
) {
    companion object {
        const val VIEW_NAME = "congregation_totals_view"
    }
}