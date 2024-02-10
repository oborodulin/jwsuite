package com.oborodulin.jwsuite.presentation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class DatabaseUiModel(
    val entityDesc: String,
    val isDone: Boolean
) : ModelUi()