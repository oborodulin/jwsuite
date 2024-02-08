package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.FavoriteCongregationView
import com.oborodulin.jwsuite.domain.util.Constants.DB_FRACT_SEC_TIME

@DatabaseView(
    viewName = TerritoryTotalView.VIEW_NAME,
    value = """
    SELECT c.*, ct.sumTotalTerritories AS totalTerritories, 
        ctm.sumTotalTerritoryIssued AS totalTerritoryIssued, ctm.sumTotalTerritoryProcessed AS totalTerritoryProcessed,
        ctd.totalTerritories AS diffTerritories, 
        ctd.totalTerritoryIssued - ifnull(cmx.totalTerritoryIssued, 0) AS diffTerritoryIssued,
        ctd.totalTerritoryProcessed - ifnull(cmx.totalTerritoryProcessed, 0) AS diffTerritoryProcessed
    FROM ${FavoriteCongregationView.VIEW_NAME} c 
        JOIN (SELECT ctlCongregationsId, SUM(totalTerritories) AS sumTotalTerritories
                FROM ${CongregationTotalEntity.TABLE_NAME}
                GROUP BY ctlCongregationsId
            ) ct ON ct.ctlCongregationsId = c.congregationId
        JOIN (SELECT ct.ctlCongregationsId, SUM(ct.totalTerritoryIssued) AS sumTotalTerritoryIssued, SUM(ct.totalTerritoryProcessed) AS sumTotalTerritoryProcessed
                FROM ${CongregationTotalEntity.TABLE_NAME} ct LEFT JOIN (SELECT ctlCongregationsId, MAX(strftime($DB_FRACT_SEC_TIME, lastVisitDate)) AS maxLastVisitDate 
                                                                        FROM ${CongregationTotalEntity.TABLE_NAME} WHERE lastVisitDate IS NOT NULL
                                                                        GROUP BY ctlCongregationsId) cm
                    ON ct.ctlCongregationsId = ifnull(cm.ctlCongregationsId, ct.ctlCongregationsId)
                        AND (cm.maxLastVisitDate IS NULL
                            OR strftime($DB_FRACT_SEC_TIME, ct.lastVisitDate) BETWEEN cm.maxLastVisitDate AND strftime($DB_FRACT_SEC_TIME, datetime('now', 'localtime')))
                GROUP BY ct.ctlCongregationsId
            ) ctm ON ctm.ctlCongregationsId = c.congregationId
    LEFT JOIN (SELECT ct.ctlCongregationsId, ct.totalTerritoryIssued, ct.totalTerritoryProcessed
                FROM ${CongregationTotalEntity.TABLE_NAME} ct JOIN (SELECT ctlCongregationsId, MAX(strftime($DB_FRACT_SEC_TIME, lastVisitDate)) AS maxLastVisitDate 
                                                                    FROM ${CongregationTotalEntity.TABLE_NAME} WHERE lastVisitDate IS NOT NULL
                                                                    GROUP BY ctlCongregationsId) cm
                    ON ct.ctlCongregationsId = cm.ctlCongregationsId AND strftime($DB_FRACT_SEC_TIME, ct.lastVisitDate) = cm.maxLastVisitDate
            ) cmx ON cmx.ctlCongregationsId = c.congregationId
    JOIN ${CongregationTotalEntity.TABLE_NAME} ctd ON ctd.ctlCongregationsId = c.congregationId AND ctd.lastVisitDate IS NULL
"""
)
class TerritoryTotalView(
    @Embedded val congregation: FavoriteCongregationView,
    val totalTerritories: Int,
    val totalTerritoryIssued: Int,
    val totalTerritoryProcessed: Int,
    val diffTerritories: Int,
    val diffTerritoryIssued: Int,
    val diffTerritoryProcessed: Int
) {
    companion object {
        const val VIEW_NAME = "territory_totals_view"
    }
}