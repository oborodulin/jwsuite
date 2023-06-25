package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model.mappers

import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationListItem
import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.home.domain.model.Payer

class PayerListToPayerListItemMapper(mapper: PayerToPayerListItemMapper) :
    ListMapperImpl<Payer, CongregationListItem>(mapper)