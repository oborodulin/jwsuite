package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalitiesListItem

class LocalitiesListToLocalityListItemMapper(mapper: LocalityToLocalitiesListItemMapper) :
    ListMapperImpl<GeoLocality, LocalitiesListItem>(mapper)