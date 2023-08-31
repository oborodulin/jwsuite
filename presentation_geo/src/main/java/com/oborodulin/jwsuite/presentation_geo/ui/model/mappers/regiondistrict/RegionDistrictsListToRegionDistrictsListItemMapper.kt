package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.regiondistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.GeoRegionDistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionDistrictsListItem

class RegionDistrictsListToRegionDistrictsListItemMapper(mapper: RegionDistrictToRegionDistrictsListItemMapper) :
    ListMapperImpl<GeoRegionDistrict, RegionDistrictsListItem>(mapper)