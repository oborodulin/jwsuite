package com.oborodulin.jwsuite.presentation.ui.modules.territoring

import java.math.BigDecimal

sealed class TerritoringUiEvent {
    data class ErcCodeChanged(val ercCode: String) : TerritoringUiEvent()
    data class FullNameChanged(val fullName: String) : TerritoringUiEvent()
    data class AddressChanged(val address: String) : TerritoringUiEvent()
    data class TotalAreaChanged(val totalArea: BigDecimal?) : TerritoringUiEvent()
    data class LivingSpaceChanged(val livingSpace: BigDecimal?) : TerritoringUiEvent()
    data class HeatedVolumeChanged(val heatedVolume: BigDecimal?) : TerritoringUiEvent()
    data class PaymentDayChanged(val paymentDay: Int?) : TerritoringUiEvent()
    data class PersonsNumChanged(val personsNum: Int?) : TerritoringUiEvent()
    object Submit : TerritoringUiEvent()
}
