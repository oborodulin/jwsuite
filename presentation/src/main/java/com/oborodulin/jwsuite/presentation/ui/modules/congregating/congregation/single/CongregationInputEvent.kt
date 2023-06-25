package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class CongregationInputEvent(val value: String) : Inputable {
    data class ErcCode(val input: String) : CongregationInputEvent(input)
    data class FullName(val input: String) : CongregationInputEvent(input)
    data class Address(val input: String) : CongregationInputEvent(input)
    data class TotalArea(val input: String) : CongregationInputEvent(input)
    data class LivingSpace(val input: String) : CongregationInputEvent(input)
    data class HeatedVolume(val input: String) : CongregationInputEvent(input)
    data class PaymentDay(val input: String) : CongregationInputEvent(input)
    data class PersonsNum(val input: String) : CongregationInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
