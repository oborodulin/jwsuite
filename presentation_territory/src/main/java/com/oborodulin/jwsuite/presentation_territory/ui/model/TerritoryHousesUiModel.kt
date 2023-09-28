package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class TerritoryHousesUiModel(
    val territory: TerritoryUi = TerritoryUi(),
    val houses: List<HousesListItem> = emptyList()
) : ModelUi()