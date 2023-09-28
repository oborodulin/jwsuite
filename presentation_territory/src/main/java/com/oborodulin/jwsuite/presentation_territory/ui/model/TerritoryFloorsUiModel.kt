package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class TerritoryFloorsUiModel(
    val territory: TerritoryUi = TerritoryUi(),
    val floors: List<FloorsListItem> = emptyList()
) : ModelUi()