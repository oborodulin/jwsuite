package com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.appsetting.AppSetting
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem

class AppSettingToAppSettingsListItemMapper :
    Mapper<AppSetting, AppSettingsListItem>, NullableMapper<AppSetting, AppSettingsListItem> {
    override fun map(input: AppSetting) = AppSettingsListItem(
        id = input.id!!,
        paramName = input.paramName,
        paramValue = input.paramValue,
        paramFullName = input.paramFullName
    )

    override fun nullableMap(input: AppSetting?) = input?.let { map(it) }
}