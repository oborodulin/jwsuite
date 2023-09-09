package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryDetailsUi

interface TerritoryDetailsViewModel :
    MviViewModeled<TerritoryDetailsUi, TerritoryDetailsUiAction, TerritoryDetailsUiSingleEvent> {
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
}