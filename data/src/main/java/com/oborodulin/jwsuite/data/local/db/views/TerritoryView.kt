package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data.util.Constants
import com.oborodulin.jwsuite.data.util.Constants.PX_CONGREGATION_LOCALITY
import com.oborodulin.jwsuite.data.util.Constants.PX_LOCALITY
import com.oborodulin.jwsuite.data.util.Constants.PX_TERRITORY

@DatabaseView(
    viewName = TerritoryView.VIEW_NAME,
    value = """
SELECT t.*, cv.*, tc.*, lv.*, ld.*, md.*, s.paramValue AS territoryBusinessMark
FROM ${TerritoryEntity.TABLE_NAME} t JOIN ${CongregationView.VIEW_NAME} cv ON cv.congregationId = t.tCongregationsId
    JOIN ${TerritoryCategoryEntity.TABLE_NAME} tc ON tc.territoryCategoryId = t.tTerritoryCategoriesId
    JOIN ${GeoLocalityView.VIEW_NAME} lv ON lv.${PX_LOCALITY}localityId = t.tLocalitiesId AND lv.${PX_LOCALITY}localityLocCode = cv.${PX_CONGREGATION_LOCALITY}localityLocCode 
    LEFT JOIN ${GeoLocalityDistrictEntity.TABLE_NAME} ld ON ld.localityDistrictId = t.tLocalityDistrictsId
    LEFT JOIN ${GeoMicrodistrictEntity.TABLE_NAME} md ON md.microdistrictId = t.tMicrodistrictsId
    JOIN ${AppSettingEntity.TABLE_NAME} s ON s.paramName = ${Constants.PRM_TERRITORY_BUSINESS_MARK_VAL}
"""
)
class TerritoryView(
    @Embedded val territory: TerritoryEntity,
    @Embedded val congregation: CongregationView,
    @Embedded val territoryCategory: TerritoryCategoryEntity,
    @Embedded val locality: GeoLocalityView,
    @Embedded val localityDistrict: GeoLocalityDistrictEntity?,
    @Embedded val microdistrict: GeoMicrodistrictEntity?,
    val territoryBusinessMark: String
) {
    companion object {
        const val VIEW_NAME = "territories_view"
    }
}