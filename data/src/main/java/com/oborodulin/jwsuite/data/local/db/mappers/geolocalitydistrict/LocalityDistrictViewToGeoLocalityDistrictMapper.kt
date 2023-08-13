package com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data.local.db.views.LocalityDistrictView
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict

class LocalityDistrictViewToGeoLocalityDistrictMapper :
    ConstructedMapper<LocalityDistrictView, GeoLocalityDistrict>,
    NullableConstructedMapper<LocalityDistrictView, GeoLocalityDistrict> {
    override fun map(input: LocalityDistrictView, vararg properties: Any?): GeoLocalityDistrict {
        if (properties.isEmpty() || properties[0] !is GeoLocality) throw IllegalArgumentException(
            "LocalityDistrictViewToGeoLocalityDistrictMapper properties is empty or properties[0] is not GeoLocality class: input.id = %s".format(
                input.data.localityDistrictId
            )
        )
        val localityDistrict = GeoLocalityDistrict(
            locality = properties[0] as GeoLocality,
            districtShortName = input.data.locDistrictShortName,
            districtName = input.tl.locDistrictName
        )
        localityDistrict.id = input.data.localityDistrictId
        localityDistrict.tlId = input.tl.localityDistrictTlId
        return localityDistrict
    }

    override fun nullableMap(input: LocalityDistrictView?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}