package com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.region

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.GeoRegion
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.RegionsListItem

class RegionsListToRegionsListItemMapper(mapper: RegionToRegionsListItemMapper) :
    ListMapperImpl<GeoRegion, RegionsListItem>(mapper)