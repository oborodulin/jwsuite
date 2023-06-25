package com.oborodulin.jwsuite.presentation.ui.modules.congregating

import java.math.BigDecimal

sealed class CongregatingUiEvent {
    data class ErcCodeChanged(val ercCode: String) : CongregatingUiEvent()
    data class FullNameChanged(val fullName: String) : CongregatingUiEvent()
    data class AddressChanged(val address: String) : CongregatingUiEvent()
    data class TotalAreaChanged(val totalArea: BigDecimal?) : CongregatingUiEvent()
    data class LivingSpaceChanged(val livingSpace: BigDecimal?) : CongregatingUiEvent()
    data class HeatedVolumeChanged(val heatedVolume: BigDecimal?) : CongregatingUiEvent()
    data class PaymentDayChanged(val paymentDay: Int?) : CongregatingUiEvent()
    data class PersonsNumChanged(val personsNum: Int?) : CongregatingUiEvent()
    object Submit : CongregatingUiEvent()
}
