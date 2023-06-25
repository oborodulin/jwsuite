package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.payer.list

import com.oborodulin.home.domain.model.Payer

data class PayersListUiState(
    val payers: List<Payer>,
    val isLoading: Boolean,
    val error: String? = null
)
