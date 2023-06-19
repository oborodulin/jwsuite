package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.CongregationEntity

@DatabaseView(
    viewName = CongregationView.VIEW_NAME,
    value = """
SELECT c.*, l.* FROM ${CongregationEntity.TABLE_NAME} c JOIN ${GeoLocalityView.VIEW_NAME} l ON l.localityId = c.localitiesId
ORDER BY c.congregationName
"""
)
class CongregationView(
    @Embedded
    val congregation: CongregationEntity,
    @Embedded
    val locality: GeoLocalityView,
) {
    companion object {
        const val VIEW_NAME = "congregations_view"
    }
}