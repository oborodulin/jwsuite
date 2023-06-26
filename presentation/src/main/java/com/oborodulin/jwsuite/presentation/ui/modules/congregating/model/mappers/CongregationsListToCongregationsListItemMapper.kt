package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem

class CongregationsListToCongregationsListItemMapper(mapper: CongregationToCongregationsListItemMapper) :
    ListMapperImpl<Congregation, CongregationsListItem>(mapper)