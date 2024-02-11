package com.oborodulin.jwsuite.presentation_territory.ui.territoring.setting

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsUiModel
import kotlinx.coroutines.flow.StateFlow

interface TerritorySettingViewModel :
    DialogViewModeled<AppSettingsUiModel, TerritorySettingUiAction, UiSingleEvent, TerritorySettingFields> {
    val territoryProcessingPeriod: StateFlow<InputWrapper>
    val territoryAtHandPeriod: StateFlow<InputWrapper>
    val territoryIdlePeriod: StateFlow<InputWrapper>
    val territoryRoomsLimit: StateFlow<InputWrapper>
    val territoryMaxRooms: StateFlow<InputWrapper>
}