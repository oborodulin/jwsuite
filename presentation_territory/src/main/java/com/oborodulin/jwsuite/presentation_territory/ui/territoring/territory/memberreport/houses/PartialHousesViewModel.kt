package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.memberreport.houses

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SingleViewModeled
import kotlinx.coroutines.flow.StateFlow

interface PartialHousesViewModel :
    SingleViewModeled<Any, PartialHousesUiAction, PartialHousesUiSingleEvent, PartialHousesFields> {
    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val street: StateFlow<InputListItemWrapper<ListItemModel>>
}