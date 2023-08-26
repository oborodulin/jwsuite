package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.StreetUi

data class TerritoryStreetUi(
    val territory: TerritoryUi = TerritoryUi(),
    val street: StreetUi = StreetUi(),
    val isEvenSide: Boolean? = null,
    val isPrivateSector: Boolean? = null,
    val estimatedHouses: Int? = null
) : ModelUi()