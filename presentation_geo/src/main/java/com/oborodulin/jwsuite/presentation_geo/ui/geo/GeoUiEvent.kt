package com.oborodulin.jwsuite.presentation_geo.ui.geo

import java.math.BigDecimal

sealed class GeoUiEvent {
    data class ErcCodeChanged(val ercCode: String) : GeoUiEvent()
    data class FullNameChanged(val fullName: String) : GeoUiEvent()
    data class AddressChanged(val address: String) : GeoUiEvent()
    data class TotalAreaChanged(val totalArea: BigDecimal?) : GeoUiEvent()
    data class LivingSpaceChanged(val livingSpace: BigDecimal?) : GeoUiEvent()
    data class HeatedVolumeChanged(val heatedVolume: BigDecimal?) : GeoUiEvent()
    data class PaymentDayChanged(val paymentDay: Int?) : GeoUiEvent()
    data class PersonsNumChanged(val personsNum: Int?) : GeoUiEvent()
    data object Submit : GeoUiEvent()
}
