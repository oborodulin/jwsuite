package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class HouseUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val houseId: UUID? = null) : HouseUiAction()
    data object Save : HouseUiAction()
}