package com.oborodulin.jwsuite.presentation.ui.model.mappers.regiondistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import com.oborodulin.jwsuite.presentation.ui.model.RegionDistrictListItem

class RegionDistrictListToRegionDistrictListItemMapper(mapper: RegionDistrictToRegionDistrictListItemMapper) :
    ListMapperImpl<GeoRegionDistrict, RegionDistrictListItem>(mapper)