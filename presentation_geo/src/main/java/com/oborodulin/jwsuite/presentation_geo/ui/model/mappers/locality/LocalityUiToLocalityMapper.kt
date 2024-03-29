package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region.RegionUiToRegionMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.regiondistrict.RegionDistrictUiToRegionDistrictMapper

class LocalityUiToLocalityMapper(
    private val ctx: Context,
    private val regionUiMapper: RegionUiToRegionMapper,
    private val regionUiDistrictMapper: RegionDistrictUiToRegionDistrictMapper
) : Mapper<LocalityUi, GeoLocality> {
    override fun map(input: LocalityUi) = GeoLocality(
        ctx = ctx,
        region = regionUiMapper.nullableMap(input.region),
        regionDistrict = regionUiDistrictMapper.nullableMap(input.regionDistrict),
        localityCode = input.localityCode,
        localityType = input.localityType,
        localityShortName = input.localityShortName,
        localityName = input.localityName
    ).also {
        it.id = input.id
        it.tlId = input.tlId
    }
}