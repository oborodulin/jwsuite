package com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.locality

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.region.RegionToRegionUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.regiondistrict.RegionDistrictToRegionDistrictUiMapper

class LocalityToLocalityUiMapper(
    private val regionMapper: RegionToRegionUiMapper,
    private val regionDistrictMapper: RegionDistrictToRegionDistrictUiMapper
) : Mapper<GeoLocality, LocalityUi> {
    override fun map(input: GeoLocality): LocalityUi {
        val localityUi = LocalityUi(
            region = regionMapper.map(input.region),
            regionDistrict = regionDistrictMapper.nullableMap(input.regionDistrict),
            localityCode = input.localityCode,
            localityType = input.localityType,
            localityShortName = input.localityShortName,
            localityName = input.localityName
        )
        localityUi.id = input.id
        return localityUi
    }
}