package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.CheckedListDialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryHousesUiModel
import kotlinx.coroutines.flow.StateFlow

interface TerritoryHouseViewModel :
    CheckedListDialogViewModeled<TerritoryHousesUiModel, TerritoryHouseUiAction, UiSingleEvent, TerritoryHouseFields, List<HousesListItem>> {
    val territory: StateFlow<InputListItemWrapper<ListItemModel>>
}