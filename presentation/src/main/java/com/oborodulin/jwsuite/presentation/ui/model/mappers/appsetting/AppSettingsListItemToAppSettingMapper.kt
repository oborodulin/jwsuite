package com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem

class AppSettingsListItemToAppSettingMapper :
    Mapper<AppSettingsListItem, com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv>, NullableMapper<AppSettingsListItem, com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv> {
    override fun map(input: AppSettingsListItem) =
        com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv(
            paramName = input.paramName,
            paramValue = input.paramValue
        )

    override fun nullableMap(input: AppSettingsListItem?) = input?.let { map(it) }
}