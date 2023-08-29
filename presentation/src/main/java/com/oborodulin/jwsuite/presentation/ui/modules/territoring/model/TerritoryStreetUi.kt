package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.StreetUi
import java.util.UUID

data class TerritoryStreetUi(
    val territoryId: UUID = UUID.randomUUID(),
    val street: StreetUi = StreetUi(),
    val isEvenSide: Boolean? = null,
    val isPrivateSector: Boolean? = null,
    val estimatedHouses: Int? = null
) : ModelUi()