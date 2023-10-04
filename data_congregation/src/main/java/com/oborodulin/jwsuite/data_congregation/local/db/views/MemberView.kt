package com.oborodulin.jwsuite.data_congregation.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME

@DatabaseView(
    viewName = MemberView.VIEW_NAME,
    value = """
SELECT cv.*, gv.*, m.*, mc.*, mm.* FROM ${MemberEntity.TABLE_NAME} m 
    LEFT JOIN ${GroupView.VIEW_NAME} gv ON gv.groupId = m.mGroupsId
    JOIN (SELECT mcMembersId, MAX(strftime($DB_FRACT_SEC_TIME, activityDate)) AS maxActivityDate 
            FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} GROUP BY mcMembersId ) gmc
        ON gmc.mcMembersId = m.memberId 
    JOIN ${MemberCongregationCrossRefEntity.TABLE_NAME} mc ON mc.mcMembersId = m.memberId AND strftime($DB_FRACT_SEC_TIME, mc.activityDate) = gmc.maxActivityDate 
                                                                AND mc.mcCongregationsId = ifnull(gv.gCongregationsId, mc.mcCongregationsId)  
    JOIN ${CongregationView.VIEW_NAME} cv ON cv.congregationId = mc.mcCongregationsId
    JOIN (SELECT mMembersId, MAX(strftime($DB_FRACT_SEC_TIME, movementDate)) AS maxMovementDate 
            FROM ${MemberMovementEntity.TABLE_NAME} GROUP BY mMembersId ) gmm 
        ON gmm.mMembersId = m.memberId
    JOIN ${MemberMovementEntity.TABLE_NAME} mm ON mm.mMembersId = m.memberId AND strftime($DB_FRACT_SEC_TIME, mm.movementDate) = gmm.maxMovementDate
"""
)
class MemberView(
    @Embedded val memberCongregation: CongregationView,
    @Embedded val group: GroupView? = null,
    @Embedded val member: MemberEntity,
    @Embedded val lastCongregation: MemberCongregationCrossRefEntity,
    @Embedded val lastMovement: MemberMovementEntity
) {
    companion object {
        const val VIEW_NAME = "members_view"
    }
}