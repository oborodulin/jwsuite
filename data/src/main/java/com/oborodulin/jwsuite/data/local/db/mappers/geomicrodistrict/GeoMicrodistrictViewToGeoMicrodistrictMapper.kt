package com.oborodulin.jwsuite.data.local.db.mappers.geomicrodistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.views.GeoMicrodistrictView
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict

class GeoMicrodistrictViewToGeoMicrodistrictMapper :
    Mapper<GeoMicrodistrictView, GeoMicrodistrict> {
    override fun map(input: GeoMicrodistrictView): GeoMicrodistrict {
        val microdistrict = GeoMicrodistrict(
            localityId = input.data.mLocalitiesId,
            localityDistrictId = input.data.mLocalityDistrictsId,
            microdistrictType = input.data.microdistrictType,
            microdistrictShortName = input.data.microdistrictShortName,
            microdistrictName = input.tl.microdistrictName
        )
        microdistrict.id = input.data.microdistrictId
        microdistrict.tlId = input.tl.microdistrictTlId
        return microdistrict
    }
}