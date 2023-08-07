package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data.util.Constants.PX_LOCALITY

@DatabaseView(
    viewName = GeoStreetView.VIEW_NAME,
    value = """
SELECT lv.*, ldv.*, mdv.*, sv.* FROM ${StreetView.VIEW_NAME} sv JOIN ${GeoLocalityView.VIEW_NAME} lv ON lv.${PX_LOCALITY}localityId = sv.sLocalitiesId
    LEFT JOIN ${GeoDistrictStreetEntity.TABLE_NAME} ds ON ds.dsStreetsId = sv.streetId
    LEFT JOIN ${GeoLocalityDistrictView.VIEW_NAME} ldv ON ldv.localityDistrictId = ds.dsLocalityDistrictsId
    LEFT JOIN ${GeoMicrodistrictView.VIEW_NAME} mdv ON mdv.microdistrictId = ds.dsMicrodistrictsId
"""
)
class GeoStreetView(
    @Embedded val locality: GeoLocalityView,
    @Embedded val district: GeoLocalityDistrictView?,
    @Embedded val microdistrict: GeoMicrodistrictView?,
    @Embedded val street: StreetView
) {
    companion object {
        const val VIEW_NAME = "geo_streets_view"
    }
}