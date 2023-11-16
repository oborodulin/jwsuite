package com.oborodulin.jwsuite.presentation.ui.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.AppSetting
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingUi

class AppSettingToAppSettingUiMapper :
    Mapper<AppSetting, AppSettingUi>, NullableMapper<AppSetting, AppSettingUi> {
    override fun map(input: AppSetting) = AppSettingUi(
        paramName = input.paramName,
        paramValue = input.paramValue
    )

    override fun nullableMap(input: AppSetting?) = input?.let { map(it) }
}