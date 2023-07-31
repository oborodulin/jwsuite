package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data.util.Constants
import com.oborodulin.jwsuite.data.util.Constants.DB_FALSE
import com.oborodulin.jwsuite.data.util.Constants.DB_TRUE
import java.util.UUID

@DatabaseView(
    viewName = TerritoriesAtWorkView.VIEW_NAME,
    value = """
SELECT t.*, m.*, ct.ctCongregationsId, ifnull(tps.isPrivateSector, $DB_FALSE) AS isPrivateSector, 
    (rld.atWorkDays - rld.territoryAtHandPeriod * 30) AS expiredDays, s.paramValue AS atWorkTerritoryBusinessMark
FROM ${TerritoryView.VIEW_NAME} t JOIN ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct 
        ON ct.ctTerritoriesId = t.territoryId AND t.isActive = $DB_TRUE AND t.isProcessed = $DB_FALSE
    LEFT JOIN ${TerritoryPrivateSectorView.VIEW_NAME} tps ON tps.territoryId = t.territoryId
    JOIN ${TerritoryMemberLastReceivingDateView.VIEW_NAME} rld ON rld.tmcTerritoriesId = t.territoryId AND rld.fullIdleMonths < 0
    JOIN ${MemberView.VIEW_NAME} m ON m.memberId = rld.tmcMembersId
    JOIN ${AppSettingEntity.TABLE_NAME} s ON s.paramName = ${Constants.PRM_TERRITORY_BUSINESS_MARK_VAL}
ORDER BY (rld.atWorkDays - rld.territoryAtHandPeriod * 30) DESC
"""
)
class TerritoriesAtWorkView(
    @Embedded val territory: TerritoryView,
    @Embedded val member: MemberView,
    val ctCongregationsId: UUID,
    val isPrivateSector: Boolean,
    val expiredDays: Int,
    val atWorkTerritoryBusinessMark: String
) {
    companion object {
        const val VIEW_NAME = "territories_at_work_view"
    }
}