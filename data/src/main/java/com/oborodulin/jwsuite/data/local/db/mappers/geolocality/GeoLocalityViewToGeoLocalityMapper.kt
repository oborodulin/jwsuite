package com.oborodulin.jwsuite.data.local.db.mappers.geolocality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionViewToGeoRegionMapper
import com.oborodulin.jwsuite.data.local.db.mappers.georegiondistrict.GeoRegionDistrictViewToGeoRegionDistrictMapper
import com.oborodulin.jwsuite.data.local.db.views.GeoLocalityView
import com.oborodulin.jwsuite.data.local.db.views.GeoRegionDistrictView
import com.oborodulin.jwsuite.domain.model.GeoLocality

class GeoLocalityViewToGeoLocalityMapper(
    private val regionMapper: GeoRegionViewToGeoRegionMapper,
    private val regionDistrictMapper: GeoRegionDistrictViewToGeoRegionDistrictMapper
) : Mapper<GeoLocalityView, GeoLocality> {
    override fun map(input: GeoLocalityView): GeoLocality {
        val locality = GeoLocality(
            region = regionMapper.map(input.region),
            regionDistrict = regionDistrictMapper.map(input.regionDistrict),
            localityCode = input.data.localityCode,
            localityType = input.data.localityType,
            localityShortName = input.tl.localityShortName,
            localityName = input.tl.localityName
        )
        locality.id = input.data.localityId
        locality.tlId = input.tl.localityTlId
        return locality
    }
}