package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class CountryUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val countryId: UUID? = null) : CountryUiAction()
    data object Save : CountryUiAction()
}