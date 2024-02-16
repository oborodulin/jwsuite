package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME

@DatabaseView(
    viewName = MemberLastCongregationView.VIEW_NAME,
    value = """
SELECT mcr.*, mcm.pseudonym AS memberPseudonym FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} mcr
    JOIN (SELECT mc.mcMembersId, m.pseudonym, MAX(strftime($DB_FRACT_SEC_TIME, mc.activityDate)) AS maxActivityDate 
            FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} mc JOIN ${MemberEntity.TABLE_NAME} m ON mc.mcMembersId = m.memberId
            GROUP BY mc.mcMembersId, m.pseudonym) mcm 
        ON mcr.mcMembersId = mcm.mcMembersId AND strftime($DB_FRACT_SEC_TIME, mcr.activityDate) = mcm.maxActivityDate
"""
)
class MemberLastCongregationView(
    @Embedded val lastMemberCongregation: MemberCongregationCrossRefEntity,
    val memberPseudonym: String
) {
    companion object {
        const val VIEW_NAME = "member_last_congregations_view"
    }
}