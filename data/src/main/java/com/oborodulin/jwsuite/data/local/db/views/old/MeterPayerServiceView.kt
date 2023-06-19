package com.oborodulin.jwsuite.data.local.db.views.old

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.old.PayerServiceCrossRefEntity
import java.util.*

@DatabaseView(
    viewName = MeterPayerServiceView.VIEW_NAME,
    value = """
SELECT m.*, s.*, ps.payerServiceId, ps.isEven, ps.isPrivileges, ps.isAllocateRate
FROM ${MeterView.VIEW_NAME} m 
    JOIN ${ServiceView.VIEW_NAME} s ON s.serviceMemberType = m.memberType AND s.serviceLocCode = m.meterLocCode
    JOIN ${PayerServiceCrossRefEntity.TABLE_NAME} ps ON ps.territoriesId = m.payersId AND ps.servicesId = s.serviceId 
ORDER BY m.payersId, s.servicePos
"""
)
class MeterPayerServiceView(
    @Embedded
    val meter: MeterView,
    @Embedded
    val service: ServiceView,
    val payerServiceId: UUID,
    val isMeterOwner: Boolean,
    val isPrivileges: Boolean,
    val isAllocateRate: Boolean
) {
    companion object {
        const val VIEW_NAME = "meter_payer_services_view"
    }
}