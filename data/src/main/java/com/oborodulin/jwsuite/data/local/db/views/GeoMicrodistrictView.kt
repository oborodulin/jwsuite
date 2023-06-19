package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoMicrodistrictTlEntity

@DatabaseView(
    viewName = GeoMicrodistrictView.VIEW_NAME,
    value = """
SELECT md.*, mdtl.* FROM ${GeoMicrodistrictEntity.TABLE_NAME} md JOIN ${GeoMicrodistrictTlEntity.TABLE_NAME} mdtl ON mdtl.microdistrictsId = md.microdistrictId
ORDER BY mdtl.microdistrictName
"""
)
class GeoMicrodistrictView(
    @Embedded
    val data: GeoMicrodistrictEntity,
    @Embedded
    val tl: GeoMicrodistrictTlEntity,
) {
    companion object {
        const val VIEW_NAME = "geo_microdistricts_view"
    }
}