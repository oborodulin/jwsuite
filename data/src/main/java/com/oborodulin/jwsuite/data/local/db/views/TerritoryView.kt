package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryEntity

@DatabaseView(
    viewName = TerritoryView.VIEW_NAME,
    value = """
SELECT t.*, cv.*, tc.*, l.*, ld.districtShortName, md.microdistrictShortName
FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${CongregationView.VIEW_NAME} cv ON cv.congregationId = t.congregationsId
    JOIN ${TerritoryCategoryEntity.TABLE_NAME} tc ON tc.territoryCategoryId = t.territoryCategoriesId
    JOIN ${GeoLocalityView.VIEW_NAME} l ON l.localityId = t.localitiesId
    LEFT JOIN ${GeoLocalityDistrictEntity.TABLE_NAME} ld ON ld.localityDistrictId = t.localityDistrictsId
    LEFT JOIN ${GeoMicrodistrictEntity.TABLE_NAME} md ON md.microdistrictId = t.microdistrictsId
ORDER BY cv.territoryMark, t.territoryNum
"""
)
class TerritoryView(
    @Embedded
    val territory: TerritoryEntity,
    @Embedded
    val congregation: CongregationView,
    @Embedded
    val territoryCategory: TerritoryCategoryEntity,
    @Embedded
    val locality: GeoLocalityView,
    val districtShortName: String?,
    val microdistrictShortName: String?
) {
    companion object {
        const val VIEW_NAME = "territories_view"
    }
}