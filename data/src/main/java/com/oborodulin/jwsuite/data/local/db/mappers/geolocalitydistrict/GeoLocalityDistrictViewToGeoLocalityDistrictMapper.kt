package com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityDistrictView
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict

class GeoLocalityDistrictViewToGeoLocalityDistrictMapper :
    Mapper<GeoLocalityDistrictView, GeoLocalityDistrict> {
    override fun map(input: GeoLocalityDistrictView): GeoLocalityDistrict {
        val localityDistrict = GeoLocalityDistrict(
            localityId = input.data.localitiesId,
            districtShortName = input.data.districtShortName,
            districtName = input.tl.districtName
        )
        localityDistrict.id = input.data.localityDistrictId
        localityDistrict.tlId = input.tl.localityDistrictsId
        return localityDistrict
    }
}