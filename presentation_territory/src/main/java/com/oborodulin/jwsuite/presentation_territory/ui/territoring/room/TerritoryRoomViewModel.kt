package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.CheckedListDialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryRoomsUiModel
import kotlinx.coroutines.flow.StateFlow

interface TerritoryRoomViewModel :
    CheckedListDialogViewModeled<TerritoryRoomsUiModel, TerritoryRoomUiAction, UiSingleEvent, TerritoryRoomFields> {
    val territory: StateFlow<InputListItemWrapper<ListItemModel>>
}