package com.oborodulin.jwsuite.presentation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.util.AppSettingParam

data class AppSettingUi(
    val paramName: AppSettingParam,
    val paramValue: String = ""
) : ModelUi()