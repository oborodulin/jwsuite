package com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.street

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoStreet
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.StreetUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.localitydistrict.LocalityDistrictUiToLocalityDistrictMapper
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.microdistrict.MicrodistrictUiToMicrodistrictMapper

class StreetUiToStreetMapper(
    private val localityUiMapper: LocalityUiToLocalityMapper,
    private val localityDistrictUiMapper: LocalityDistrictUiToLocalityDistrictMapper,
    private val microdistrictUiMapper: MicrodistrictUiToMicrodistrictMapper
) : Mapper<StreetUi, GeoStreet> {
    override fun map(input: StreetUi): GeoStreet {
        val street = GeoStreet(
            locality = localityUiMapper.map(input.locality),
            localityDistrict = localityDistrictUiMapper.nullableMap(input.localityDistrict),
            microdistrict = microdistrictUiMapper.nullableMap(input.microdistrict),
            streetHashCode = 0,
            roadType = input.roadType,
            isPrivateSector = input.isPrivateSector,
            estimatedHouses = input.estimatedHouses,
            streetName = input.streetName
        )
        street.id = input.id
        return street
    }
}