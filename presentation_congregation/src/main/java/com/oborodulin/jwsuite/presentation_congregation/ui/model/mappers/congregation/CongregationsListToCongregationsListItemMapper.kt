package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.congregation.Congregation
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem

class CongregationsListToCongregationsListItemMapper(mapper: CongregationToCongregationsListItemMapper) :
    ListMapperImpl<Congregation, CongregationsListItem>(mapper)