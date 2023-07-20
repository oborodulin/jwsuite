package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data.util.Constants.DB_TRUE
import java.util.UUID

@DatabaseView(
    viewName = TerritoriesIdleView.VIEW_NAME,
    value = """
SELECT t.*, ct.ctCongregationsId, tps.isPrivateSector
FROM ${TerritoryView.VIEW_NAME} t JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct 
        ON ct.ctTerritoriesId = t.territoryId AND t.isActive = $DB_TRUE AND t.isProcessed = $DB_TRUE
    JOIN ${TerritoryPrivateSectorView.VIEW_NAME} tps ON tps.territoryId = t.territoryId
    JOIN ${TerritoryMemberLastReceivingDateView.VIEW_NAME} rld ON rld.territoryId = t.territoryId AND rld.fullIdleMonths < rld.territoryIdlePeriod
ORDER BY (rld.territoryIdlePeriod - rld.fullIdleMonths)
"""
)
class TerritoriesIdleView(
    @Embedded val territory: TerritoryView,
    val ctCongregationsId: UUID,
    val isPrivateSector: Boolean
) {
    companion object {
        const val VIEW_NAME = "territories_idle_view"
    }
}