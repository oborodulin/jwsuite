package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding

import java.math.BigDecimal

sealed class DashboardingUiEvent {
    data class ErcCodeChanged(val ercCode: String) : DashboardingUiEvent()
    data class FullNameChanged(val fullName: String) : DashboardingUiEvent()
    data class AddressChanged(val address: String) : DashboardingUiEvent()
    data class TotalAreaChanged(val totalArea: BigDecimal?) : DashboardingUiEvent()
    data class LivingSpaceChanged(val livingSpace: BigDecimal?) : DashboardingUiEvent()
    data class HeatedVolumeChanged(val heatedVolume: BigDecimal?) : DashboardingUiEvent()
    data class PaymentDayChanged(val paymentDay: Int?) : DashboardingUiEvent()
    data class PersonsNumChanged(val personsNum: Int?) : DashboardingUiEvent()
    object Submit : DashboardingUiEvent()
}
