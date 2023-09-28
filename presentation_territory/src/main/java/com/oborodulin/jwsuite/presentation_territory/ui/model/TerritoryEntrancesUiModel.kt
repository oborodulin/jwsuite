package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class TerritoryEntrancesUiModel(
    val territory: TerritoryUi = TerritoryUi(),
    val entrances: List<EntrancesListItem> = emptyList()
) : ModelUi()