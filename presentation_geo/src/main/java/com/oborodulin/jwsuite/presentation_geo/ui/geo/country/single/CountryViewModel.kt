package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_geo.ui.model.CountryUi
import kotlinx.coroutines.flow.StateFlow

interface CountryViewModel :
    DialogViewModeled<CountryUi, CountryUiAction, UiSingleEvent, CountryFields> {
    val countryCode: StateFlow<InputWrapper>
    val countryName: StateFlow<InputWrapper>
    val countryGeocode: StateFlow<InputWrapper>
    val countryOsmId: StateFlow<InputWrapper>
    val latitude: StateFlow<InputWrapper>
    val longitude: StateFlow<InputWrapper>
}