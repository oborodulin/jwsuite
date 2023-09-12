package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class TerritoryHouseUiModel(
    val territory: TerritoryUi = TerritoryUi(),
    val house: HouseUi = HouseUi()
) : ModelUi()