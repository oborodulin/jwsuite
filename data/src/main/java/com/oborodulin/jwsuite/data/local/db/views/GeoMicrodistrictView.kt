package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.data.util.Constants

@DatabaseView(
    viewName = GeoMicrodistrictView.VIEW_NAME,
    value = """
SELECT lv.*, ldv.*, mdv.* 
FROM ${MicrodistrictView.VIEW_NAME} mdv JOIN ${LocalityDistrictView.VIEW_NAME} ldv ON ldv.localityDistrictId = mdv.mLocalityDistrictsId AND ldv.locDistrictLocCode = mdv.microdistrictLocCode
    JOIN ${GeoLocalityView.VIEW_NAME} lv ON lv.${Constants.PX_LOCALITY}localityId = ldv.ldLocalitiesId and lv.${Constants.PX_LOCALITY}localityLocCode = ldv.locDistrictLocCode
"""
)
class GeoMicrodistrictView(
    @Embedded val locality: GeoLocalityView,
    @Embedded val district: LocalityDistrictView,
    @Embedded val microdistrict: MicrodistrictView
) {
    companion object {
        const val VIEW_NAME = "geo_microdistricts_view"
    }
}