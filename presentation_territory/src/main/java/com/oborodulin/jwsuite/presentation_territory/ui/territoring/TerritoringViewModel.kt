package com.oborodulin.jwsuite.presentation_territory.ui.territoring

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.state.SingleViewModeled
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoringUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryLocationsListItem
import kotlinx.coroutines.flow.StateFlow

interface TerritoringViewModel :
    SingleViewModeled<TerritoringUi, TerritoringUiAction, TerritoringUiSingleEvent, TerritoringFields> {
    val isPrivateSector: StateFlow<InputWrapper>
    val location: StateFlow<InputListItemWrapper<TerritoryLocationsListItem>>
}