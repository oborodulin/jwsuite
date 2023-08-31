package com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.GeoMicrodistrict
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictsListItem

class MicrodistrictsListToMicrodistrictsListItemMapper(mapper: MicrodistrictToMicrodistrictsListItemMapper) :
    ListMapperImpl<GeoMicrodistrict, MicrodistrictsListItem>(mapper)