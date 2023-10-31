package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.state.CheckedListDialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetMicrodistrictsUiModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import kotlinx.coroutines.flow.StateFlow

interface StreetMicrodistrictViewModel :
    CheckedListDialogViewModeled<StreetMicrodistrictsUiModel, StreetMicrodistrictUiAction, UiSingleEvent, StreetMicrodistrictFields> {
    val street: StateFlow<InputListItemWrapper<StreetsListItem>>
}