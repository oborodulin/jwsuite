package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.region

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.geo.GeoRegion
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionsListItem

class RegionsListToRegionsListItemMapper(mapper: RegionToRegionsListItemMapper) :
    ListMapperImpl<GeoRegion, RegionsListItem>(mapper)