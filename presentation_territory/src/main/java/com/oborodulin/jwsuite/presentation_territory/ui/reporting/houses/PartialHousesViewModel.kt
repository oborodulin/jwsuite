package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SingleViewModeled
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryHouseReportsListItem
import kotlinx.coroutines.flow.StateFlow

interface PartialHousesViewModel :
    SingleViewModeled<List<TerritoryHouseReportsListItem>, PartialHousesUiAction, PartialHousesUiSingleEvent, PartialHousesFields> {
    val territoryStreet: StateFlow<InputListItemWrapper<ListItemModel>>
}