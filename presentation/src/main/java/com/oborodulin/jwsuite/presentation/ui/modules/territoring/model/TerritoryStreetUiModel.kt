package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.StreetsListItem

data class TerritoryStreetUiModel(
    val territoryStreet: TerritoryStreetUi = TerritoryStreetUi(),
    val territory: TerritoryUi = TerritoryUi(),
    val streets: List<StreetsListItem> = emptyList()
) : ModelUi()