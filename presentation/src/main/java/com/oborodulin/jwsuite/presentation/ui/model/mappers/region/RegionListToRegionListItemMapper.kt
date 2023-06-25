package com.oborodulin.jwsuite.presentation.ui.model.mappers.region

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.GeoRegion
import com.oborodulin.jwsuite.presentation.ui.model.RegionListItem

class RegionListToRegionListItemMapper(mapper: RegionToRegionListItemMapper) :
    ListMapperImpl<GeoRegion, RegionListItem>(mapper)