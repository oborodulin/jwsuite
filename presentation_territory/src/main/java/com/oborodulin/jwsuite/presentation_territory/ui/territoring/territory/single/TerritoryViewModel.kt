package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryCategoriesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryUi
import kotlinx.coroutines.flow.StateFlow

interface TerritoryViewModel :
    DialogViewModeled<TerritoryUi, TerritoryUiAction, UiSingleEvent, TerritoryFields> {
    val congregation: StateFlow<InputListItemWrapper<CongregationsListItem>>
    val category: StateFlow<InputListItemWrapper<TerritoryCategoriesListItem>>
    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val microdistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val territoryNum: StateFlow<InputWrapper>
    val isBusiness: StateFlow<InputWrapper>
    val isGroupMinistry: StateFlow<InputWrapper>
    val isActive: StateFlow<InputWrapper>
    val territoryDesc: StateFlow<InputWrapper>

    fun onInsert(block: () -> Unit)
}