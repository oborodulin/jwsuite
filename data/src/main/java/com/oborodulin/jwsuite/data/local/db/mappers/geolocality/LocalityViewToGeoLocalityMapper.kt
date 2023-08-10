package com.oborodulin.jwsuite.data.local.db.mappers.geolocality

import com.oborodulin.home.common.mapping.ConstructedMapper
import com.oborodulin.home.common.mapping.NullableConstructedMapper
import com.oborodulin.jwsuite.data.local.db.views.LocalityView
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.domain.model.GeoRegion
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict

class LocalityViewToGeoLocalityMapper : ConstructedMapper<LocalityView, GeoLocality>,
    NullableConstructedMapper<LocalityView, GeoLocality> {
    override fun map(input: LocalityView, vararg properties: Any?): GeoLocality {
        if (properties.size != 2 || properties[0] !is GeoRegion || properties[1] !is GeoRegionDistrict) throw IllegalArgumentException(
            "Constructed Mapper properties size not equal 2 or properties[0] is not GeoRegion class or properties[1] is not GeoRegionDistrict class: size = %d".format(
                properties.size
            )
        )
        val locality = GeoLocality(
            region = properties[0] as GeoRegion,
            regionDistrict = properties[1] as? GeoRegionDistrict,
            localityCode = input.data.localityCode,
            localityType = input.data.localityType,
            localityShortName = input.tl.localityShortName,
            localityName = input.tl.localityName
        )
        locality.id = input.data.localityId
        locality.tlId = input.tl.localityTlId
        return locality
    }

    override fun nullableMap(input: LocalityView?, vararg properties: Any?) =
        input?.let { map(it, properties) }
}