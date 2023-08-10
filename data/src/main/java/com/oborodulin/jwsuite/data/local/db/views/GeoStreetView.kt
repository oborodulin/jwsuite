package com.oborodulin.jwsuite.data.local.db.views

import androidx.room.DatabaseView
import androidx.room.Embedded
import com.oborodulin.jwsuite.data.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data.util.Constants
import com.oborodulin.jwsuite.data.util.Constants.PX_LOCALITY
import com.oborodulin.jwsuite.data.util.Constants.PX_LOCALITY_DISTRICT

@DatabaseView(
    viewName = GeoStreetView.VIEW_NAME,
    value = """
SELECT lv.*, ldv.*, mdv.*, sv.* 
FROM ${StreetView.VIEW_NAME} sv JOIN ${GeoLocalityView.VIEW_NAME} lv ON lv.${PX_LOCALITY}localityId = sv.sLocalitiesId AND lv.${PX_LOCALITY}localityLocCode = sv.streetLocCode
    LEFT JOIN ${GeoDistrictStreetEntity.TABLE_NAME} ds ON ds.dsStreetsId = sv.streetId
    LEFT JOIN ${GeoLocalityDistrictView.VIEW_NAME} ldv ON ldv.${PX_LOCALITY_DISTRICT}localityDistrictId = ds.dsLocalityDistrictsId AND ldv.${PX_LOCALITY_DISTRICT}locDistrictLocCode = sv.streetLocCode
    LEFT JOIN ${GeoMicrodistrictView.VIEW_NAME} mdv ON mdv.microdistrictId = ds.dsMicrodistrictsId AND mdv.microdistrictLocCode = sv.streetLocCode
"""
)
class GeoStreetView(
    @Embedded val locality: GeoLocalityView,
    @Embedded val localityDistrict: GeoLocalityDistrictView?,
    @Embedded val microdistrict: GeoMicrodistrictView?,
    @Embedded val street: StreetView
) {
    companion object {
        const val VIEW_NAME = "geo_streets_view"
    }
}