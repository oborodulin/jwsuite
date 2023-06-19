package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityTlEntity

@DatabaseView(
    viewName = GeoLocalityView.VIEW_NAME,
    value = """
SELECT l.*, ltl.* FROM ${GeoLocalityEntity.TABLE_NAME} l JOIN ${GeoLocalityTlEntity.TABLE_NAME} ltl ON ltl.localitiesId = l.localityId
ORDER BY ltl.localityName
"""
)
class GeoLocalityView(
    @Embedded
    val data: GeoLocalityEntity,
    @Embedded
    val tl: GeoLocalityTlEntity,
) {
    companion object {
        const val VIEW_NAME = "geo_localities_view"
    }
}