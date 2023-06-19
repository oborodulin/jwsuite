package com.oborodulin.jwsuite.data.local.db.views.old

import androidx.room.DatabaseView
import com.oborodulin.jwsuite.data.local.db.entities.old.MeterEntity
import com.oborodulin.jwsuite.data.local.db.entities.old.MeterValueEntity
import com.oborodulin.jwsuite.data.local.db.entities.old.PayerEntity
import com.oborodulin.jwsuite.data.util.Constants
import java.time.OffsetDateTime
import java.util.*

@DatabaseView(
    viewName = MeterValueMaxPrevDateView.VIEW_NAME,
    value = """
SELECT mv.villageId, MAX(mv.valueDate) AS maxValueDate 
FROM (SELECT m.villageId, m.streetsId, 
            ifnull(strftime(${Constants.DB_FRACT_SEC_TIME}, v.valueDate), 
                   strftime(${Constants.DB_FRACT_SEC_TIME}, 'now', 'localtime', 'start of month', '-1 days')) AS valueDate 
        FROM ${MeterEntity.TABLE_NAME} m JOIN ${MeterValueEntity.TABLE_NAME} v ON v.metersId = m.villageId
        UNION ALL
        SELECT m.meterId, m.payersId, strftime(${Constants.DB_FRACT_SEC_TIME}, m.passportDate) AS valueDate FROM ${MeterView.VIEW_NAME} m
        WHERE m.passportDate IS NOT NULL AND m.initValue IS NOT NULL) mv 
    JOIN ${PayerEntity.TABLE_NAME} p ON p.payerId = mv.streetsId
 WHERE mv.valueDate <= 
    CASE WHEN p.isAlignByPaymentDay = 0 
        THEN strftime(${Constants.DB_FRACT_SEC_TIME}, 'now', 'localtime', 'start of month', '-1 days')
        ELSE CASE WHEN datetime('now', 'localtime') > datetime('now', 'localtime', 'start of month', '+' || (ifnull(p.paymentDay, ${Constants.DEF_PAYMENT_DAY}) - 1) || ' days')
                THEN strftime(${Constants.DB_FRACT_SEC_TIME}, 'now', 'localtime', 'start of month', '+' || (ifnull(p.paymentDay, ${Constants.DEF_PAYMENT_DAY}) - 1) || ' days')
                ELSE strftime(${Constants.DB_FRACT_SEC_TIME}, 'now', 'localtime', '-1 months', 'start of month', '+' || (ifnull(p.paymentDay, ${Constants.DEF_PAYMENT_DAY}) - 1) || ' days')
            END
    END
GROUP BY mv.villageId
"""
)
class MeterValueMaxPrevDateView(
    val meterId: UUID,
    val maxValueDate: OffsetDateTime
) {
    companion object {
        const val VIEW_NAME = "meter_value_max_prev_dates_view"
    }
}