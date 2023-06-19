package com.oborodulin.jwsuite.data.local.db.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityView
import com.oborodulin.jwsuite.domain.model.GeoLocality

class GeoLocalityViewToGeoLocalityMapper : Mapper<GeoLocalityView, GeoLocality> {
    override fun map(input: GeoLocalityView): GeoLocality {
        val locality = GeoLocality(
            regionId = input.data.regionsId,
            regionDistrictId = input.data.regionDistrictsId,
            localityCode = input.data.localityCode,
            localityType = input.data.localityType,
            localityName = input.tl.localityName
        )
        locality.id = input.data.localityId
        locality.tlId = input.tl.localityTlId
        return locality
    }
}