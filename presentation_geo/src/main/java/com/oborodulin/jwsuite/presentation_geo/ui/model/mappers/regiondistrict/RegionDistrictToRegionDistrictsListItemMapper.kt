package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.regiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionDistrictsListItem
import java.util.UUID

class RegionDistrictToRegionDistrictsListItemMapper :
    Mapper<GeoRegionDistrict, RegionDistrictsListItem> {
    override fun map(input: GeoRegionDistrict) = RegionDistrictsListItem(
        id = input.id ?: UUID.randomUUID(),
        districtShortName = input.districtShortName,
        districtName = input.districtName
    )
}