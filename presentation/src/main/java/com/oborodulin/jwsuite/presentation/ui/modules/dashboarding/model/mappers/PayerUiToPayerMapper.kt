package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model.mappers

import com.oborodulin.jwsuite.presentation.ui.congregating.model.CongregationUi
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.domain.model.Payer

class PayerUiToPayerMapper : Mapper<CongregationUi, Payer> {
    override fun map(input: CongregationUi): Payer {
        val payer = Payer(
            ercCode = input.congregationNum,
            fullName = input.congregationName,
            address = input.territoryMark,
            totalArea = input.totalArea,
            livingSpace = input.livingSpace,
            heatedVolume = input.heatedVolume,
            paymentDay = input.paymentDay,
            personsNum = input.personsNum,
            isAlignByPaymentDay = input.isAlignByPaymentDay,
            isFavorite = input.isFavorite
        )
        payer.id = input.id
        return payer
    }
}