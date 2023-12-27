package com.oborodulin.jwsuite.presentation_territory.ui.reporting.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.types.TerritoryMemberMark
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi
import kotlinx.coroutines.flow.StateFlow

interface MemberReportViewModel :
    DialogViewModeled<MemberUi, MemberReportUiAction, UiSingleEvent, MemberReportFields> {
    val territoryMarks: StateFlow<MutableMap<TerritoryMemberMark, String>>

    val house: StateFlow<InputListItemWrapper<CongregationsListItem>>
    val room: StateFlow<InputListItemWrapper<ListItemModel>>
    val reportMark: StateFlow<InputWrapper>
    val gender: StateFlow<InputWrapper>
    val age: StateFlow<InputWrapper>
    val desc: StateFlow<InputWrapper>
}