package com.oborodulin.jwsuite.presentation.ui.model.mappers.locality

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.GeoLocality
import com.oborodulin.jwsuite.presentation.ui.model.LocalitiesListItem

class LocalitiesListToLocalityListItemMapper(mapper: LocalityToLocalitiesListItemMapper) :
    ListMapperImpl<GeoLocality, LocalitiesListItem>(mapper)