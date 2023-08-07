package com.oborodulin.jwsuite.presentation.ui.modules.geo.model.mappers.localitydistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.GeoLocalityDistrict
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.LocalityDistrictsListItem

class LocalityDistrictsListToLocalityDistrictsListItemMapper(mapper: LocalityDistrictToLocalityDistrictsListItemMapper) :
    ListMapperImpl<GeoLocalityDistrict, LocalityDistrictsListItem>(mapper)