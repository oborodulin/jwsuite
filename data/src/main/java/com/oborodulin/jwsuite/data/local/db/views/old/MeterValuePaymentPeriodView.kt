package com.oborodulin.jwsuite.data.local.db.views.old

import androidx.room.DatabaseView
import com.oborodulin.jwsuite.data.local.db.entities.old.MeterValueEntity
import com.oborodulin.jwsuite.data.local.db.entities.old.PayerEntity
import com.oborodulin.jwsuite.data.local.db.entities.old.PayerServiceCrossRefEntity
import com.oborodulin.jwsuite.data.util.Constants
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

@DatabaseView(
    viewName = MeterValuePaymentPeriodView.VIEW_NAME,
    value = """
SELECT mvl.*, lv.measureUnit, lv.isDerivedUnit, lv.derivedUnit, lv.meterLocCode, lv.maxValue,
    lv.payerId, lv.payerServiceId, lv.servicePos,
    strftime(${Constants.DB_FRACT_SEC_TIME}, datetime(lv.paymentDate, 'localtime')) || 
        printf('%+.2d:%.2d', round((julianday(lv.paymentDate, 'localtime') - julianday(lv.paymentDate)) * 24), 
            abs(round((julianday(lv.paymentDate, 'localtime') - julianday(lv.paymentDate)) * 24 * 60) % 60)) AS paymentDate, 
    CAST(strftime('%m', lv.paymentDate) AS INTEGER) AS paymentMonth, 
    CAST(strftime('%Y', lv.paymentDate) AS INTEGER) AS paymentYear
FROM (SELECT v.meterValueId, v.valueDate, v.meterValue, v.metersId AS meterId FROM ${MeterValueEntity.TABLE_NAME} v
    UNION ALL
    SELECT NULL AS meterValueId, m.passportDate AS valueDate, m.initValue AS meterValue, m.meterId FROM ${MeterView.VIEW_NAME} m
    WHERE m.passportDate IS NOT NULL AND m.initValue IS NOT NULL) mvl JOIN 
        (SELECT mvp.payerId, ps.payerServiceId, mvp.meterId, MAX(strftime(${Constants.DB_FRACT_SEC_TIME}, mvp.valueDate)) maxValueDate, 
            mvp.paymentDate, ifnull(sv.serviceMeasureUnit, mvp.meterMeasureUnit) AS measureUnit, sv.servicePos,
            mvp.isDerivedUnit, mvp.derivedUnit, mvp.meterLocCode, mvp.maxValue
        FROM (SELECT mv.meterId, p.payerId, mv.memberType, v.valueDate, mv.meterMeasureUnit, mv.isDerivedUnit, 
                    mv.derivedUnit, mv.meterLocCode, mv.maxValue,
                    (CASE WHEN p.isAlignByPaymentDay = 0
                        THEN strftime(${Constants.DB_FRACT_SEC_TIME}, v.valueDate, 'start of month', '+1 months')
                        ELSE
                            CASE WHEN datetime(v.valueDate) 
                                    BETWEEN datetime(v.valueDate, 'start of month') 
                                        AND datetime(v.valueDate, 'start of month', '+' || (ifnull(p.paymentDay, ${Constants.DEF_PAYMENT_DAY}) - 1) || ' days') 
                                THEN strftime(${Constants.DB_FRACT_SEC_TIME}, v.valueDate, 'start of month')
                                WHEN datetime(v.valueDate) 
                                    BETWEEN datetime(v.valueDate, 'start of month', '+' || ifnull(p.paymentDay, ${Constants.DEF_PAYMENT_DAY}) || ' days') 
                                        AND datetime(v.valueDate, '+1 months', 'start of month') 
                                THEN strftime(${Constants.DB_FRACT_SEC_TIME}, v.valueDate, '+1 months', 'start of month') 
                            END
                    END) paymentDate
                FROM ${MeterValueEntity.TABLE_NAME} v JOIN ${MeterView.VIEW_NAME} mv ON mv.meterId = v.metersId
                    JOIN ${PayerEntity.TABLE_NAME} p ON p.payerId = mv.payersId
                UNION ALL
                SELECT m.meterId, m.payersId AS payerId, m.memberType, m.passportDate AS valueDate, m.meterMeasureUnit, m.isDerivedUnit, 
                    m.derivedUnit, m.meterLocCode, m.maxValue, strftime(${Constants.DB_FRACT_SEC_TIME}, m.passportDate, 'start of month', '+1 months') AS paymentDate 
                FROM ${MeterView.VIEW_NAME} m
                WHERE m.passportDate IS NOT NULL AND m.initValue IS NOT NULL) mvp
            JOIN ${ServiceView.VIEW_NAME} sv ON sv.serviceMemberType = mvp.memberType AND sv.serviceLocCode = mvp.meterLocCode
            JOIN ${PayerServiceCrossRefEntity.TABLE_NAME} ps ON ps.territoriesId = mvp.payerId AND ps.servicesId = sv.serviceId 
        GROUP BY mvp.payerId, ps.payerServiceId, mvp.meterId, mvp.paymentDate, sv.serviceMeasureUnit, mvp.meterMeasureUnit,
                mvp.isDerivedUnit, mvp.derivedUnit, mvp.meterLocCode, mvp.maxValue) lv 
        ON mvl.meterId = lv.meterId AND strftime(${Constants.DB_FRACT_SEC_TIME}, mvl.valueDate) = lv.maxValueDate
"""
)
class MeterValuePaymentPeriodView(
    val meterValueId: UUID?,
    val valueDate: OffsetDateTime,
    val meterValue: BigDecimal? = null,
    val meterId: UUID,
    val measureUnit: String,
    val isDerivedUnit: Boolean,
    val derivedUnit: String,
    val meterLocCode: String,
    val maxValue: BigDecimal,
    val payerId: UUID,
    val payerServiceId: UUID,
    val servicePos: Int, // for testing purpose
    val paymentDate: OffsetDateTime,
    val paymentMonth: Int,
    val paymentYear: Int
) {
    companion object {
        const val VIEW_NAME = "meter_value_payment_periods_view"
    }
}