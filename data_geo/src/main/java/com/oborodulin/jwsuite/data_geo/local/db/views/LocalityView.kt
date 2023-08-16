package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityTlEntity

@DatabaseView(
    viewName = LocalityView.VIEW_NAME,
    value = "SELECT l.*, ltl.* FROM ${GeoLocalityEntity.TABLE_NAME} l JOIN ${GeoLocalityTlEntity.TABLE_NAME} ltl ON ltl.localitiesId = l.localityId"
)
class LocalityView(
    @Embedded val data: GeoLocalityEntity,
    @Embedded val tl: GeoLocalityTlEntity
) {
    companion object {
        const val VIEW_NAME = "localities_view"
    }
}