package com.oborodulin.jwsuite.presentation_congregation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class GroupUi(
    val congregation: CongregationUi = CongregationUi(),
    val groupNum: Int? = null
) : ModelUi()