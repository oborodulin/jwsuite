package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list

import com.oborodulin.home.domain.model.Payer

data class RegionDistrictsListUiState(
    val payers: List<Payer>,
    val isLoading: Boolean,
    val error: String? = null
)
