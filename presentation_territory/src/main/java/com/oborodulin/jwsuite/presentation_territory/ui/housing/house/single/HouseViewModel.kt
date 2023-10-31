package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.util.BuildingType
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.HouseUi
import kotlinx.coroutines.flow.StateFlow

interface HouseViewModel :
    DialogViewModeled<HouseUi, HouseUiAction, UiSingleEvent, HouseFields> {
    val buildingTypes: StateFlow<MutableMap<BuildingType, String>>

    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val street: StateFlow<InputListItemWrapper<StreetsListItem>>
    val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val microdistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val territory: StateFlow<InputListItemWrapper<ListItemModel>>
    val zipCode: StateFlow<InputWrapper>
    val houseNum: StateFlow<InputWrapper>
    val houseLetter: StateFlow<InputWrapper>
    val buildingNum: StateFlow<InputWrapper>
    val buildingType: StateFlow<InputWrapper>
    val isBusiness: StateFlow<InputWrapper>
    val isSecurity: StateFlow<InputWrapper>
    val isIntercom: StateFlow<InputWrapper>
    val isResidential: StateFlow<InputWrapper>
    val houseEntrancesQty: StateFlow<InputWrapper>
    val floorsByEntrance: StateFlow<InputWrapper>
    val roomsByHouseFloor: StateFlow<InputWrapper>
    val estimatedRooms: StateFlow<InputWrapper>
    val isForeignLanguage: StateFlow<InputWrapper>
    val isPrivateSector: StateFlow<InputWrapper>
    val houseDesc: StateFlow<InputWrapper>
}