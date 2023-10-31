package com.oborodulin.jwsuite.presentation_territory.ui.housing

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SingleViewModeled
import kotlinx.coroutines.flow.StateFlow

interface HousingViewModel :
    SingleViewModeled<Any, HousingUiAction, HousingUiSingleEvent, HousingFields> {
    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val street: StateFlow<InputListItemWrapper<ListItemModel>>
}