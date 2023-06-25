package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list

import com.oborodulin.home.domain.model.Payer

data class LocalitiesListUiState(
    val payers: List<Payer>,
    val isLoading: Boolean,
    val error: String? = null
)
