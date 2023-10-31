package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.util.VillageType
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictUi
import kotlinx.coroutines.flow.StateFlow

interface MicrodistrictViewModel :
    DialogViewModeled<MicrodistrictUi, MicrodistrictUiAction, UiSingleEvent, MicrodistrictFields> {
    val microdistrictTypes: StateFlow<MutableMap<VillageType, String>>

    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val microdistrictShortName: StateFlow<InputWrapper>
    val microdistrictType: StateFlow<InputWrapper>
    val microdistrictName: StateFlow<InputWrapper>
}