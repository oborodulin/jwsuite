package com.oborodulin.jwsuite.presentation_territory.ui.housing.room.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomUi
import kotlinx.coroutines.flow.StateFlow

interface RoomViewModel : DialogViewModeled<RoomUi, RoomUiAction, UiSingleEvent, RoomFields> {
    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val microdistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val street: StateFlow<InputListItemWrapper<StreetsListItem>>
    val house: StateFlow<InputListItemWrapper<HousesListItem>>
    val entrance: StateFlow<InputListItemWrapper<ListItemModel>>
    val floor: StateFlow<InputListItemWrapper<ListItemModel>>
    val territory: StateFlow<InputListItemWrapper<ListItemModel>>
    val roomNum: StateFlow<InputWrapper>
    val isIntercom: StateFlow<InputWrapper>
    val isResidential: StateFlow<InputWrapper>
    val isForeignLanguage: StateFlow<InputWrapper>
    val roomDesc: StateFlow<InputWrapper>
}