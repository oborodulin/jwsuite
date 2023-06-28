package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model

import com.oborodulin.home.common.ui.model.ModelUi

data class GroupUi(
    val congregation: CongregationUi,
    val groupNum: Int
) : ModelUi()