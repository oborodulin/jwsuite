package com.oborodulin.jwsuite.data.local.db.views.old

import androidx.room.DatabaseView
import com.oborodulin.jwsuite.data.local.db.entities.old.MeterEntity
import com.oborodulin.jwsuite.data.local.db.entities.old.PayerEntity
import com.oborodulin.jwsuite.data.local.db.entities.old.RateEntity
import com.oborodulin.jwsuite.data.local.db.entities.old.ServiceEntity
import com.oborodulin.jwsuite.domain.util.RoadType
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.*

@DatabaseView(
    viewName = RatePayerServiceView.VIEW_NAME,
    value = """
SELECT rps.*, (CASE WHEN EXISTS (SELECT m.villageId 
                                FROM ${MeterEntity.TABLE_NAME} m JOIN ${ServiceEntity.TABLE_NAME} s 
                                    ON s.serviceMemberType = m.memberType AND m.streetsId = rps.payerId 
                                        AND s.serviceId = rps.serviceId)
                    THEN 1 
                    ELSE 0 
                END) isMeterUses
FROM (-- Services rates for payer services
    SELECT p.payerId, p.personsNum, p.totalArea, p.livingSpace, p.heatedVolume, psv.payerServiceId, psv.isAllocateRate, 
        psv.serviceId, psv.serviceName, psv.servicePos, psv.roadType, psv.serviceLocCode, psv.serviceMeasureUnit, 
        psv.fromServiceDate,
        CAST(strftime('%m', psv.fromServiceDate) AS INTEGER) AS fromServiceMonth,
        CAST(strftime('%Y', psv.fromServiceDate) AS INTEGER) AS fromServiceYear,
        r.startDate, r.fromMeterValue, r.toMeterValue, r.rateValue, r.isPerPerson, r.isPrivileges
    FROM ${RateEntity.TABLE_NAME} r JOIN ${PayerServiceView.VIEW_NAME} psv ON psv.servicesId = r.servicesId  
            AND psv.isPrivileges = r.isPrivileges
            AND NOT EXISTS(SELECT rateId FROM ${RateEntity.TABLE_NAME} WHERE payersServicesId = psv.payerServiceId)
        JOIN ${PayerEntity.TABLE_NAME} p ON p.payerId = psv.payersId 
    WHERE r.payersServicesId IS NULL
    UNION ALL
    -- Payer services rates
    SELECT p.payerId, p.personsNum, p.totalArea, p.livingSpace, p.heatedVolume, psv.payerServiceId, psv.isAllocateRate, 
        psv.serviceId, psv.serviceName, psv.servicePos, psv.roadType, psv.serviceLocCode, psv.serviceMeasureUnit, 
        psv.fromServiceDate,
        CAST(strftime('%m', psv.fromServiceDate) AS INTEGER) AS fromServiceMonth,
        CAST(strftime('%Y', psv.fromServiceDate) AS INTEGER) AS fromServiceYear,
        r.startDate, r.fromMeterValue, r.toMeterValue, r.rateValue, r.isPerPerson, r.isPrivileges
    FROM ${PayerEntity.TABLE_NAME} p JOIN ${PayerServiceView.VIEW_NAME} psv ON psv.payersId = p.payerId
        JOIN ${RateEntity.TABLE_NAME} r ON r.payersServicesId = psv.payerServiceId AND r.isPrivileges = psv.isPrivileges) rps
"""
)
class RatePayerServiceView(
    val payerId: UUID,
    val personsNum: Int,
    val totalArea: BigDecimal?,
    val livingSpace: BigDecimal?,
    val heatedVolume: BigDecimal?,
    val payerServiceId: UUID,
    val isAllocateRate: Boolean,
    val serviceId: UUID,
    val serviceName: String,
    val servicePos: Int,
    val roadType: RoadType,
    val serviceLocCode: String,
    val serviceMeasureUnit: String?,
    val fromServiceDate: OffsetDateTime?,
    val fromServiceMonth: Int?,
    val fromServiceYear: Int?,
    val startDate: OffsetDateTime,
    val fromMeterValue: BigDecimal?,
    val toMeterValue: BigDecimal?,
    val rateValue: BigDecimal,
    val isPerPerson: Boolean,
    val isPrivileges: Boolean,
    val isMeterUses: Boolean
) {
    companion object {
        const val VIEW_NAME = "rate_payer_services_view"
    }
}