package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.details

import com.oborodulin.home.common.ui.state.MviViewModeled
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryDetailsUi
import kotlinx.coroutines.flow.StateFlow

interface TerritoryDetailsViewModel :
    MviViewModeled<TerritoryDetailsUi, TerritoryDetailsUiAction, TerritoryDetailsUiSingleEvent> {
    val detailsTabType: StateFlow<TerritoryDetailsTabType>

    fun setDetailsTabType(tabType: TerritoryDetailsTabType)
}