package com.oborodulin.jwsuite.data_territory.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity

@DatabaseView(
    viewName = CongregationTerritoryView.VIEW_NAME,
    value = "SELECT ct.*, c.* FROM ${CongregationTerritoryCrossRefEntity.TABLE_NAME} ct JOIN ${CongregationEntity.TABLE_NAME} c ON ct.ctCongregationsId = c.congregationId"
)
class CongregationTerritoryView(
    @Embedded val congregationTerritory: CongregationTerritoryCrossRefEntity,
    @Embedded val congregation: CongregationEntity
) {
    companion object {
        const val VIEW_NAME = "congregation_territories_view"
    }
}