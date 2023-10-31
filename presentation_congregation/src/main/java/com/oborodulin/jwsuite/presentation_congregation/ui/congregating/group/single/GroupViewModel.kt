package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupUi
import kotlinx.coroutines.flow.StateFlow

interface GroupViewModel : DialogViewModeled<GroupUi, GroupUiAction, UiSingleEvent, GroupFields> {
    val congregation: StateFlow<InputListItemWrapper<CongregationsListItem>>
    val groupNum: StateFlow<InputWrapper>
}