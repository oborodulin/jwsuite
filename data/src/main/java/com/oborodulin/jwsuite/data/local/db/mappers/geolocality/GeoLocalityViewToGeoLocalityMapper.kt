package com.oborodulin.jwsuite.data.local.db.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityView
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict

class GeoLocalityViewToGeoLocalityMapper(private val regionMapper: GeoRegionViewToGeoRegionMapper) :
    Mapper<GeoLocalityView, GeoLocality> {
    override fun map(input: GeoLocalityView): GeoLocality {
        val region = regionMapper.map(input.region)
        var regionDistrict: GeoRegionDistrict? = null
        input.district?.let {
            regionDistrict = GeoRegionDistrict(
                region = region,
                districtShortName = it.data.regDistrictShortName,
                districtName = it.tl.regDistrictName
            )
            regionDistrict!!.id = it.data.regionDistrictId
            regionDistrict!!.tlId = it.tl.regionDistrictTlId
        }
        val locality = GeoLocality(
            region = regionMapper.map(input.region),
            regionDistrict = regionDistrict,
            localityCode = input.locality.data.localityCode,
            localityType = input.locality.data.localityType,
            localityShortName = input.locality.tl.localityShortName,
            localityName = input.locality.tl.localityName
        )
        locality.id = input.locality.data.localityId
        locality.tlId = input.locality.tl.localityTlId
        return locality
    }
}