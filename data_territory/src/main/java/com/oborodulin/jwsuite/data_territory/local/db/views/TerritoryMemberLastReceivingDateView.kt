package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME
import com.oborodulin.jwsuite.domain.util.Constants.PRM_TERRITORY_AT_HAND_PERIOD_VAL
import com.oborodulin.jwsuite.domain.util.Constants.PRM_TERRITORY_IDLE_PERIOD_VAL

@DatabaseView(
    viewName = TerritoryMemberLastReceivingDateView.VIEW_NAME,
    value = """
SELECT tmc.*,
    (julianday(datetime('now', 'localtime')) - julianday(ifnull(tmc.deliveryDate, datetime('now', 'localtime')))) handOutTotalDays,
    (julianday(ifnull(tmc.deliveryDate, datetime('now', 'localtime'))) - julianday(tmc.receivingDate)) atWorkTotalDays,
    (CASE WHEN tmc.deliveryDate IS NULL
        THEN -1
        ELSE (strftime('%Y', datetime('now', 'localtime'), 'start of month', '-1 day') * 12 + strftime('%m', datetime('now', 'localtime'), 'start of month', '-1 day') -
            strftime('%Y', tmc.deliveryDate) * 12 - strftime('%m', tmc.deliveryDate) + 
                (strftime('%d', datetime('now', 'localtime'), '+1 day') = '01' OR 
                strftime('%d', datetime('now', 'localtime')) >= strftime('%d', tmc.deliveryDate)))
    END) AS fullIdleMonths,
    (SELECT CAST(paramValue AS INTEGER) FROM ${AppSettingEntity.TABLE_NAME} WHERE paramName = $PRM_TERRITORY_IDLE_PERIOD_VAL) AS territoryIdlePeriod,
    (SELECT CAST(paramValue AS INTEGER) FROM ${AppSettingEntity.TABLE_NAME} WHERE paramName = $PRM_TERRITORY_AT_HAND_PERIOD_VAL) AS territoryAtHandPeriod
FROM ${TerritoryMemberCrossRefEntity.TABLE_NAME} tmc
    JOIN (SELECT tm.tmcTerritoriesId AS territoryId, MAX(strftime($DB_FRACT_SEC_TIME, tm.receivingDate)) AS maxReceivingDate 
            FROM ${TerritoryMemberCrossRefEntity.TABLE_NAME} tm 
            GROUP BY tm.tmcTerritoriesId) mtm ON mtm.territoryId = tmc.tmcTerritoriesId AND mtm.maxReceivingDate = strftime($DB_FRACT_SEC_TIME, tmc.receivingDate)
"""
)
class TerritoryMemberLastReceivingDateView(
    @Embedded val territoryMember: TerritoryMemberCrossRefEntity,
    val handOutTotalDays: Int,
    val atWorkTotalDays: Int,
    val fullIdleMonths: Int,
    val territoryIdlePeriod: Int,
    val territoryAtHandPeriod: Int
) {
    companion object {
        const val VIEW_NAME = "territory_member_last_receiving_date_view"
    }
}