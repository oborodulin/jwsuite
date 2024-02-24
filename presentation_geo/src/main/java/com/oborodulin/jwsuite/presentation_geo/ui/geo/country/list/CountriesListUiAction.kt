package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class CountriesListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data object Load : CountriesListUiAction()
    data class EditCountry(val countryId: UUID) : CountriesListUiAction()
    data class DeleteCountry(val countryId: UUID) : CountriesListUiAction()
}