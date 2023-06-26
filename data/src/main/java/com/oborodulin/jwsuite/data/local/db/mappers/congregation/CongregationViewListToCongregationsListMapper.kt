package com.oborodulin.jwsuite.data.local.db.mappers.congregation

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.CongregationView
import com.oborodulin.jwsuite.domain.model.Congregation

class CongregationViewListToCongregationsListMapper(mapper: CongregationViewToCongregationMapper) :
    ListMapperImpl<CongregationView, Congregation>(mapper)