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
SELECT m.*, mc.*, mm.*, gv.* FROM ${MemberEntity.TABLE_NAME} m JOIN ${GroupView.VIEW_NAME} gv ON gv.groupId = m.mGroupsId
     JOIN (SELECT mrMembersId, MAX(strftime(${DB_FRACT_SEC_TIME}, activityDate)) AS maxActivityDate 
            FROM ${MemberCongregationCrossRefEntity.TABLE_NAME} GROUP BY mrMembersId ) gmc
        ON gmc.mrMembersId = m.memberId 
     JOIN ${MemberCongregationCrossRefEntity.TABLE_NAME} mc ON mc.mrMembersId = m.memberId AND strftime(${DB_FRACT_SEC_TIME}, mc.activityDate) = gmc.maxActivityDate 
                                                                AND mc.mcCongregationsId = gv.gCongregationsId  
     JOIN (SELECT mMembersId, MAX(strftime(${DB_FRACT_SEC_TIME}, movementDate)) AS maxMovementDate 
            FROM ${MemberMovementEntity.TABLE_NAME} GROUP BY mMembersId ) gmm 
        ON gmm.mMembersId = m.memberId
     JOIN ${MemberMovementEntity.TABLE_NAME} mm ON mm.mMembersId = m.memberId AND strftime(${DB_FRACT_SEC_TIME}, mm.movementDate) = gmm.maxMovementDate
"""
)
class MemberView(
    @Embedded val member: MemberEntity,
    @Embedded val congregation: MemberCongregationCrossRefEntity,
    @Embedded val movement: MemberMovementEntity,
    @Embedded val group: GroupView
) {
    companion object {
        const val VIEW_NAME = "members_view"
    }
}