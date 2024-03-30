package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.types.RegionType
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionUi
import kotlinx.coroutines.flow.StateFlow

interface RegionViewModel :
    DialogViewModeled<RegionUi, RegionUiAction, UiSingleEvent, RegionFields> {
    val regionTypes: StateFlow<MutableMap<RegionType, String>>

    val tlId: StateFlow<InputWrapper>
    val country: StateFlow<InputListItemWrapper<ListItemModel>>
    val codePrefix: StateFlow<InputWrapper>
    val regionCode: StateFlow<InputWrapper>
    val regionType: StateFlow<InputWrapper>
    val isRegionTypePrefix: StateFlow<InputWrapper>
    val regionName: StateFlow<InputWrapper>
    val regionGeocode: StateFlow<InputWrapper>
    val regionOsmId: StateFlow<InputWrapper>
    val latitude: StateFlow<InputWrapper>
    val longitude: StateFlow<InputWrapper>
}