package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import com.oborodulin.home.common.ui.model.ModelUi

data class TerritoryStreetUiModel(
    val territory: TerritoryUi = TerritoryUi(),
    val territoryStreet: TerritoryStreetUi = TerritoryStreetUi(),
    val territoryStreets: List<TerritoryStreetsListItem> = emptyList()
) : ModelUi()