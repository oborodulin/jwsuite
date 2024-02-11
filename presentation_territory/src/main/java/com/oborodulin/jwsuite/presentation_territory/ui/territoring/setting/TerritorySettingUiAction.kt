package com.oborodulin.jwsuite.presentation_territory.ui.territoring.setting

import com.oborodulin.home.common.ui.state.UiAction

sealed class TerritorySettingUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data object Load : TerritorySettingUiAction()
    data object Save : TerritorySettingUiAction()
}