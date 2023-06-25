package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model.mappers

import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationUi
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.domain.model.Payer

class PayerToPayerUiMapper : Mapper<Payer, CongregationUi> {
    override fun map(input: Payer) =
        CongregationUi(
            id = input.id,
            congregationNum = input.ercCode,
            congregationName = input.fullName,
            territoryMark = input.address,
            totalArea = input.totalArea,
            livingSpace = input.livingSpace,
            heatedVolume = input.heatedVolume,
            paymentDay = input.paymentDay,
            personsNum = input.personsNum,
            isAlignByPaymentDay = input.isAlignByPaymentDay,
            isFavorite = input.isFavorite
        )
}