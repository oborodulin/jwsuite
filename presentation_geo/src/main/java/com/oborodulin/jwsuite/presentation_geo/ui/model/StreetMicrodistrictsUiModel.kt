package com.oborodulin.jwsuite.presentation_geo.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class StreetMicrodistrictsUiModel(
    val street: StreetUi = StreetUi(),
    val microdistricts: List<MicrodistrictsListItem> = emptyList()
) : ModelUi()