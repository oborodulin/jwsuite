package com.oborodulin.jwsuite.presentation.ui.model.mappers.regiondistrict

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import com.oborodulin.jwsuite.presentation.ui.model.RegionDistrictListItem
import java.util.UUID

class RegionDistrictToRegionDistrictListItemMapper :
    Mapper<GeoRegionDistrict, RegionDistrictListItem> {
    override fun map(input: GeoRegionDistrict) = RegionDistrictListItem(
        id = input.id ?: UUID.randomUUID(),
        districtShortName = input.districtShortName,
        districtName = input.districtName
    )
}