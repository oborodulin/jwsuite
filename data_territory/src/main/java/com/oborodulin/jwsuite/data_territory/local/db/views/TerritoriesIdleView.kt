package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FALSE
import com.oborodulin.jwsuite.domain.util.Constants.DB_TRUE
import com.oborodulin.jwsuite.domain.util.Constants.PRM_TERRITORY_BUSINESS_MARK_VAL
import java.util.UUID

@DatabaseView(
    viewName = TerritoriesIdleView.VIEW_NAME,
    value = """
SELECT t.*, ct.ctCongregationsId, ifnull(tps.isPrivateSector, $DB_FALSE) AS isPrivateSector, s.paramValue AS idleTerritoryBusinessMark
FROM ${TerritoryView.VIEW_NAME} t JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct 
        ON ct.ctTerritoriesId = t.territoryId AND t.isActive = $DB_TRUE AND t.isProcessed = $DB_TRUE
    LEFT JOIN ${TerritoryPrivateSectorView.VIEW_NAME} tps ON tps.territoryId = t.territoryId
    JOIN ${TerritoryMemberLastReceivingDateView.VIEW_NAME} rld ON rld.tmcTerritoriesId = t.territoryId AND rld.fullIdleMonths < rld.territoryIdlePeriod
    JOIN ${AppSettingEntity.TABLE_NAME} s ON s.paramName = $PRM_TERRITORY_BUSINESS_MARK_VAL
ORDER BY (rld.territoryIdlePeriod - rld.fullIdleMonths)
"""
)
class TerritoriesIdleView(
    @Embedded val territory: TerritoryView,
    val ctCongregationsId: UUID,
    val isPrivateSector: Boolean,
    val idleTerritoryBusinessMark: String
) {
    companion object {
        const val VIEW_NAME = "territories_idle_view"
    }
}