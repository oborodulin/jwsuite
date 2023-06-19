package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.AppSettingParam

data class AppSetting(
    val paramName: AppSettingParam,
    val paramValue: String = ""
) : DomainModel()
