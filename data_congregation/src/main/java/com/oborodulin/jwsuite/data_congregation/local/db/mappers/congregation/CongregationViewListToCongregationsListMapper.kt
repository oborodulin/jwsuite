package com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationView
import com.oborodulin.jwsuite.domain.model.congregation.Congregation

class CongregationViewListToCongregationsListMapper(mapper: CongregationViewToCongregationMapper) :
    ListMapperImpl<CongregationView, Congregation>(mapper)