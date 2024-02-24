package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionUi
import kotlinx.coroutines.flow.StateFlow

interface RegionViewModel :
    DialogViewModeled<RegionUi, RegionUiAction, UiSingleEvent, RegionFields> {
    val country: StateFlow<InputListItemWrapper<ListItemModel>>
    val regionCode: StateFlow<InputWrapper>
    val regionName: StateFlow<InputWrapper>
    val regionGeocode: StateFlow<InputWrapper>
    val regionOsmId: StateFlow<InputWrapper>
    val latitude: StateFlow<InputWrapper>
    val longitude: StateFlow<InputWrapper>
}