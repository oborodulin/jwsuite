package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data.util.Constants
import com.oborodulin.jwsuite.data.util.Constants.PX_LOCALITY

@DatabaseView(
    viewName = GeoLocalityDistrictView.VIEW_NAME,
    value = """
SELECT lv.*, ldv.*  
FROM ${LocalityDistrictView.VIEW_NAME} ldv JOIN ${GeoLocalityView.VIEW_NAME} lv ON lv.${PX_LOCALITY}localityId = ldv.ldLocalitiesId and lv.${PX_LOCALITY}localityLocCode = ldv.locDistrictLocCode
"""
)
class GeoLocalityDistrictView(
    @Embedded val locality: GeoLocalityView,
    @Embedded val district: LocalityDistrictView
) {
    companion object {
        const val VIEW_NAME = "geo_locality_districts_view"
    }
}