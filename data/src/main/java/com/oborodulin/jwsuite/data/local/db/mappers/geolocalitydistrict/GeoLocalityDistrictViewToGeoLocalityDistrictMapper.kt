package com.oborodulin.jwsuite.data.local.db.mappers.geolocalitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.geolocality.GeoLocalityViewToGeoLocalityMapper
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityDistrictView
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict

class GeoLocalityDistrictViewToGeoLocalityDistrictMapper(private val mapper: GeoLocalityViewToGeoLocalityMapper) :
    Mapper<GeoLocalityDistrictView, GeoLocalityDistrict> {
    override fun map(input: GeoLocalityDistrictView): GeoLocalityDistrict {
        val localityDistrict = GeoLocalityDistrict(
            locality = mapper.map(input.locality),
            districtShortName = input.district.data.locDistrictShortName,
            districtName = input.district.tl.locDistrictName
        )
        localityDistrict.id = input.district.data.localityDistrictId
        localityDistrict.tlId = input.district.tl.localityDistrictsId
        return localityDistrict
    }
}