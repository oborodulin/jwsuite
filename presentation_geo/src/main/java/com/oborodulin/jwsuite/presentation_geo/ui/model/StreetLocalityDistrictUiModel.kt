package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class StreetLocalityDistrictUiModel(
    val street: StreetUi = StreetUi(),
    val localityDistricts: List<LocalityDistrictsListItem> = emptyList()
) : ModelUi()