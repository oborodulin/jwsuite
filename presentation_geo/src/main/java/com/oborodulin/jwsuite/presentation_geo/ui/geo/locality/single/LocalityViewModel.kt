package com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.types.LocalityType
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface LocalityViewModel :
    DialogViewModeled<LocalityUi, LocalityUiAction, UiSingleEvent, LocalityFields> {
    val localityTypes: StateFlow<MutableMap<LocalityType, String>>

    val region: StateFlow<InputListItemWrapper<ListItemModel>>
    val regionDistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val localityCode: StateFlow<InputWrapper>
    val localityShortName: StateFlow<InputWrapper>
    val localityType: StateFlow<InputWrapper>
    val localityName: StateFlow<InputWrapper>

    fun viewModelScope(): CoroutineScope
}