package com.oborodulin.jwsuite.data_geo.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityDistrictView
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict

class LocalityDistrictViewToGeoLocalityDistrictMapper :
    ConstructedMapper<LocalityDistrictView, GeoLocalityDistrict>,
    NullableConstructedMapper<LocalityDistrictView, GeoLocalityDistrict> {
    override fun map(input: LocalityDistrictView, vararg properties: Any?): GeoLocalityDistrict {
        if (properties.isEmpty() || properties[0] !is GeoLocality) throw IllegalArgumentException(
            "LocalityDistrictViewToGeoLocalityDistrictMapper: properties is empty or properties[0] is not GeoLocality class: size = %d; input.data.localityDistrictId = %s".format(
                properties.size, input.data.localityDistrictId
            )
        )
        return GeoLocalityDistrict(
            locality = properties[0] as GeoLocality,
            districtShortName = input.data.locDistrictShortName,
            districtName = input.tl.locDistrictName
        ).also {
            it.id = input.data.localityDistrictId
            it.tlId = input.tl.localityDistrictTlId
        }
    }

    override fun nullableMap(input: LocalityDistrictView?, vararg properties: Any?) =
        input?.let { map(it, *properties) }
}