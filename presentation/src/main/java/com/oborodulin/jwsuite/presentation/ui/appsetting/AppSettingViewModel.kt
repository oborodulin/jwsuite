package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.util.MemberType
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsUiModel
import kotlinx.coroutines.flow.StateFlow

interface AppSettingViewModel :
    DialogViewModeled<AppSettingsUiModel, AppSettingUiAction, UiSingleEvent, AppSettingFields> {
    val memberTypes: StateFlow<MutableMap<MemberType, String>>

    val territoryProcessingPeriod: StateFlow<InputWrapper>
    val territoryAtHandPeriod: StateFlow<InputWrapper>
    val territoryIdlePeriod: StateFlow<InputWrapper>
    val territoryRoomsLimit: StateFlow<InputWrapper>
    val territoryMaxRooms: StateFlow<InputWrapper>
}