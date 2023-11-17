package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.util.MemberType
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsUiModel
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import kotlinx.coroutines.flow.StateFlow

interface AppSettingViewModel :
    DialogViewModeled<AppSettingsUiModel, AppSettingUiAction, UiSingleEvent, AppSettingFields> {
    val memberTypes: StateFlow<MutableMap<MemberType, String>>

    val congregation: StateFlow<InputListItemWrapper<CongregationsListItem>>
    val group: StateFlow<InputListItemWrapper<ListItemModel>>
    val territoryProcessingPeriod: StateFlow<InputWrapper>
    val territoryAtHandPeriod: StateFlow<InputWrapper>
    val territoryIdlePeriod: StateFlow<InputWrapper>
    val territoryRoomsLimit: StateFlow<InputWrapper>
    val territoryMaxRooms: StateFlow<InputWrapper>
    val phoneNumber: StateFlow<InputWrapper>
    val dateOfBirth: StateFlow<InputWrapper>
    val dateOfBaptism: StateFlow<InputWrapper>
    val memberType: StateFlow<InputWrapper>
    val movementDate: StateFlow<InputWrapper>
    val loginExpiredDate: StateFlow<InputWrapper>
}