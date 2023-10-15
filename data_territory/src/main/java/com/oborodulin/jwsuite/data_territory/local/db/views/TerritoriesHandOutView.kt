package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FALSE
import com.oborodulin.jwsuite.domain.util.Constants.DB_TRUE
import com.oborodulin.jwsuite.domain.util.Constants.PRM_TERRITORY_BUSINESS_MARK_VAL
import java.util.UUID

@DatabaseView(
    viewName = TerritoriesHandOutView.VIEW_NAME,
    value = """
SELECT tv.*, mv.*, ct.ctCongregationsId, ifnull(tps.isPrivateSector, $DB_FALSE) AS isPrivateSector, rld.handOutTotalDays, 
        s.paramValue AS handOutTerritoryBusinessMark
FROM ${TerritoryView.VIEW_NAME} tv JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct 
        ON ct.ctTerritoriesId = tv.territoryId AND tv.isActive = $DB_TRUE AND tv.isProcessed = $DB_TRUE
    LEFT JOIN ${TerritoryPrivateSectorView.VIEW_NAME} tps ON tps.territoryId = tv.territoryId
    LEFT JOIN ${TerritoryMemberLastReceivingDateView.VIEW_NAME} rld ON rld.tmcTerritoriesId = tv.territoryId
    LEFT JOIN ${MemberView.VIEW_NAME} mv ON mv.memberId = rld.tmcMembersId
    JOIN ${AppSettingEntity.TABLE_NAME} s ON s.paramName = $PRM_TERRITORY_BUSINESS_MARK_VAL
WHERE rld.fullIdleMonths IS NULL OR rld.fullIdleMonths >= rld.territoryIdlePeriod
ORDER BY ifnull(rld.fullIdleMonths - rld.territoryIdlePeriod, 1000) DESC
"""
)
class TerritoriesHandOutView(
    @Embedded val territory: TerritoryView,
    @Embedded val member: MemberView?,
    val ctCongregationsId: UUID,
    val isPrivateSector: Boolean,
    val handOutTotalDays: Int?,
    val handOutTerritoryBusinessMark: String
) {
    companion object {
        const val VIEW_NAME = "territories_hand_out_view"
    }
}