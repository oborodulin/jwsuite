package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model.mappers

import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationListItem
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.domain.model.Payer
import java.util.*

class PayerToPayerListItemMapper : Mapper<Payer, CongregationListItem> {
    override fun map(input: Payer) =
        CongregationListItem(
            id = input.id ?: UUID.randomUUID(),
            fullName = input.fullName,
            address = input.address,
            totalArea = input.totalArea,
            livingSpace = input.livingSpace,
            paymentDay = input.paymentDay,
            personsNum = input.personsNum,
            isAlignByPaymentDay = input.isAlignByPaymentDay,
            isFavorite = input.isFavorite
        )
}