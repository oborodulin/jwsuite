package com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.appsetting.AppSetting
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem

class AppSettingsListItemToAppSettingMapper :
    Mapper<AppSettingsListItem, AppSetting>, NullableMapper<AppSettingsListItem, AppSetting> {
    override fun map(input: AppSettingsListItem) = AppSetting(
        paramName = input.paramName,
        paramValue = input.paramValue
    )

    override fun nullableMap(input: AppSettingsListItem?) = input?.let { map(it) }
}