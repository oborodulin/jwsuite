package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation_territory.ui.modules.geo.model.StreetsListItem

data class TerritoryStreetUiModel(
    val territoryStreet: TerritoryStreetUi = TerritoryStreetUi(),
    val territory: TerritoryUi = TerritoryUi(),
    val streets: List<StreetsListItem> = emptyList()
) : ModelUi()