package com.oborodulin.jwsuite.presentation.ui.model.mappers.locality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.presentation.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation.ui.model.mappers.region.RegionUiToRegionMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.regiondistrict.RegionDistrictUiToRegionDistrictMapper

class LocalityUiToLocalityMapper(
    private val regionUiMapper: RegionUiToRegionMapper,
    private val regionUiDistrictMapper: RegionDistrictUiToRegionDistrictMapper
) : Mapper<LocalityUi, GeoLocality> {
    override fun map(input: LocalityUi): GeoLocality {
        val locality = GeoLocality(
            region = regionUiMapper.map(input.region),
            regionDistrict = regionUiDistrictMapper.map(input.regionDistrict),
            localityCode = input.localityCode,
            localityType = input.localityType,
            localityShortName = input.localityShortName,
            localityName = input.localityName
        )
        locality.id = input.id
        return locality
    }
}