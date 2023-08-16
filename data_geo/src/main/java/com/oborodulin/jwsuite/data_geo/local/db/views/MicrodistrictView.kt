package com.oborodulin.jwsuite.data_geo.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity

@DatabaseView(
    viewName = MicrodistrictView.VIEW_NAME,
    value = "SELECT md.*, mdtl.* FROM ${GeoMicrodistrictEntity.TABLE_NAME} md JOIN ${GeoMicrodistrictTlEntity.TABLE_NAME} mdtl ON mdtl.microdistrictsId = md.microdistrictId"
)
class MicrodistrictView(
    @Embedded val data: GeoMicrodistrictEntity,
    @Embedded val tl: GeoMicrodistrictTlEntity
) {
    companion object {
        const val VIEW_NAME = "microdistricts_view"
    }
}