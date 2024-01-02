package com.oborodulin.jwsuite.presentation_territory.ui.reporting.rooms

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SingleViewModeled
import kotlinx.coroutines.flow.StateFlow

interface PartialRoomsViewModel :
    SingleViewModeled<Any, ReportRoomsUiAction, PartialRoomsUiSingleEvent, PartialRoomsFields> {
    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val street: StateFlow<InputListItemWrapper<ListItemModel>>
}