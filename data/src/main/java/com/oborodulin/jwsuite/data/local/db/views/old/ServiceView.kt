package com.oborodulin.jwsuite.data.local.db.views.old

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.old.ServiceEntity
import com.oborodulin.jwsuite.data.local.db.entities.old.ServiceTlEntity

@DatabaseView(
    viewName = ServiceView.VIEW_NAME,
    value = """
SELECT s.*, stl.* FROM ${ServiceEntity.TABLE_NAME} s JOIN ${ServiceTlEntity.TABLE_NAME} stl ON stl.servicesId = s.serviceId
ORDER BY s.servicePos
"""
)
class ServiceView(
    @Embedded
    val data: ServiceEntity,
    @Embedded
    val tl: ServiceTlEntity,
) {
    companion object {
        const val VIEW_NAME = "services_view"
    }
}