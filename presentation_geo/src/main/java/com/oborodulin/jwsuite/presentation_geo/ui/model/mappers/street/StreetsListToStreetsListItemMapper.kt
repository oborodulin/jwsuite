package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.GeoStreet
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem

class StreetsListToStreetsListItemMapper(mapper: StreetToStreetsListItemMapper) :
    ListMapperImpl<GeoStreet, StreetsListItem>(mapper)