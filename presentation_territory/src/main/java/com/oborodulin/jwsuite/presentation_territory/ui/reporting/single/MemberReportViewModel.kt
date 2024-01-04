package com.oborodulin.jwsuite.presentation_territory.ui.reporting.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.types.TerritoryReportMark
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryMemberReportUi
import kotlinx.coroutines.flow.StateFlow

interface MemberReportViewModel :
    DialogViewModeled<TerritoryMemberReportUi, MemberReportUiAction, UiSingleEvent, MemberReportFields> {
    val reportMarks: StateFlow<MutableMap<TerritoryReportMark, String>>

    val territoryStreet: StateFlow<InputListItemWrapper<ListItemModel>>
    val house: StateFlow<InputListItemWrapper<ListItemModel>>
    val room: StateFlow<InputListItemWrapper<ListItemModel>>
    val reportMark: StateFlow<InputWrapper>
    val language: StateFlow<InputListItemWrapper<ListItemModel>>
    val gender: StateFlow<InputWrapper>
    val age: StateFlow<InputWrapper>
    val isProcessed: StateFlow<InputWrapper>
    val desc: StateFlow<InputWrapper>
}