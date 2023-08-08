package com.oborodulin.jwsuite.data.local.db.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.RegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityView
import com.oborodulin.jwsuite.domain.model.GeoLocality

class GeoLocalityViewToGeoLocalityMapper(
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: RegionDistrictViewToGeoRegionDistrictMapper
) :
    Mapper<GeoLocalityView, GeoLocality> {
    override fun map(input: GeoLocalityView): GeoLocality {
        val region = regionMapper.map(input.region)
        val locality = GeoLocality(
            region = region,
            regionDistrict = regionDistrictMapper.nullableMap(input.district, region),
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