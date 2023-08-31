package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictsListItem
import java.util.UUID

class LocalityDistrictToLocalityDistrictsListItemMapper :
    Mapper<GeoLocalityDistrict, LocalityDistrictsListItem> {
    override fun map(input: GeoLocalityDistrict) = LocalityDistrictsListItem(
        id = input.id ?: UUID.randomUUID(),
        districtShortName = input.districtShortName,
        districtName = input.districtName
    )
}