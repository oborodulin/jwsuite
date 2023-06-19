package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryEntity

@DatabaseView(
    viewName = TerritoryView.VIEW_NAME,
    value = """
SELECT t.*, cv.*, tc.* FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${CongregationView.VIEW_NAME} cv ON cv.congregationId = t.congregationsId
    JOIN ${TerritoryCategoryEntity.TABLE_NAME} tc ON tc.territoryCategoryId = t.territoryCategoriesId
ORDER BY cv.territoryMark, t.territoryNum
"""
)
class TerritoryView(
    @Embedded
    val territory: TerritoryEntity,
    @Embedded
    val congregation: CongregationView,
    @Embedded
    val territoryCategory: TerritoryCategoryEntity
) {
    companion object {
        const val VIEW_NAME = "territories_view"
    }
}