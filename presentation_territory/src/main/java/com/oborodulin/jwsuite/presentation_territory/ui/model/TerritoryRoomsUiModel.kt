package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class TerritoryRoomsUiModel(
    val territory: TerritoryUi = TerritoryUi(),
    val rooms: List<RoomsListItem> = emptyList()
) : ModelUi()