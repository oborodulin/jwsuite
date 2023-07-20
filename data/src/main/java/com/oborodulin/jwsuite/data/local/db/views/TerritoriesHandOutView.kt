package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data.util.Constants.DB_TRUE
import java.util.UUID

@DatabaseView(
    viewName = TerritoriesHandOutView.VIEW_NAME,
    value = """
SELECT t.*, m.*, ct.ctCongregationsId, tps.isPrivateSector
FROM ${TerritoryView.VIEW_NAME} t JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct 
        ON ct.ctTerritoriesId = t.territoryId AND t.isActive = $DB_TRUE AND t.isProcessed = $DB_TRUE
    JOIN ${TerritoryPrivateSectorView.VIEW_NAME} tps ON tps.territoryId = t.territoryId
    LEFT JOIN ${TerritoryMemberLastReceivingDateView.VIEW_NAME} rld ON rld.territoryId = t.territoryId
    LEFT JOIN ${MemberView.VIEW_NAME} m ON m.memberId = rld.tmcMembersId
WHERE rld.fullIdleMonths IS NULL OR rld.fullIdleMonths >= rld.territoryIdlePeriod
ORDER BY ifnull(rld.fullIdleMonths - rld.territoryIdlePeriod, 1000) DESC
"""
)
class TerritoriesHandOutView(
    @Embedded val territory: TerritoryView,
    @Embedded val member: MemberView,
    val ctCongregationsId: UUID,
    val isPrivateSector: Boolean
) {
    companion object {
        const val VIEW_NAME = "territories_hand_out_view"
    }
}