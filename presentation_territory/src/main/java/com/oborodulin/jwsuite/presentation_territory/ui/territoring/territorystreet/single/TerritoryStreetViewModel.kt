package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUiModel
import kotlinx.coroutines.flow.StateFlow

interface TerritoryStreetViewModel :
    DialogViewModeled<TerritoryStreetUiModel, TerritoryStreetUiAction, UiSingleEvent, TerritoryStreetFields> {
    val territory: StateFlow<InputListItemWrapper<ListItemModel>>
    val street: StateFlow<InputListItemWrapper<StreetsListItem>>
    val isPrivateSector: StateFlow<InputWrapper>
    val isEvenSide: StateFlow<InputWrapper>
    val estimatedHouses: StateFlow<InputWrapper>
    val isCreateEstHouses: StateFlow<InputWrapper>
}