package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionDistrictUi
import kotlinx.coroutines.flow.StateFlow

interface RegionDistrictViewModel :
    DialogViewModeled<RegionDistrictUi, RegionDistrictUiAction, UiSingleEvent, RegionDistrictFields> {
    val tlId: StateFlow<InputWrapper>
    val country: StateFlow<InputListItemWrapper<ListItemModel>>
    val region: StateFlow<InputListItemWrapper<ListItemModel>>
    val districtShortName: StateFlow<InputWrapper>
    val districtName: StateFlow<InputWrapper>
}