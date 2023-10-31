package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.state.CheckedListDialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetLocalityDistrictsUiModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import kotlinx.coroutines.flow.StateFlow

interface StreetLocalityDistrictViewModel :
    CheckedListDialogViewModeled<StreetLocalityDistrictsUiModel, StreetLocalityDistrictUiAction, UiSingleEvent, StreetLocalityDistrictFields> {
    val street: StateFlow<InputListItemWrapper<StreetsListItem>>
}