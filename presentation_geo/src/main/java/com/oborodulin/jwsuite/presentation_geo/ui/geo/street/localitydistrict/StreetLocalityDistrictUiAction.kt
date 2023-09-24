package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class StreetLocalityDistrictUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val territoryId: UUID) : StreetLocalityDistrictUiAction()
    data class Save(val houseIds: List<UUID>) : StreetLocalityDistrictUiAction()
}