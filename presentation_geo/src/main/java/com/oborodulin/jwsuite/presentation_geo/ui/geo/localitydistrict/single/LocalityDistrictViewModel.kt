package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictUi
import kotlinx.coroutines.flow.StateFlow

interface LocalityDistrictViewModel :
    DialogViewModeled<LocalityDistrictUi, LocalityDistrictUiAction, UiSingleEvent, LocalityDistrictFields> {
    val tlId: StateFlow<InputWrapper>
    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val districtShortName: StateFlow<InputWrapper>
    val districtName: StateFlow<InputWrapper>
}