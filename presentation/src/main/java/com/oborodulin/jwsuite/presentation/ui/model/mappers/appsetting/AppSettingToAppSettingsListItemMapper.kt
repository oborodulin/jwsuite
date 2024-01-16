package com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem

class AppSettingToAppSettingsListItemMapper :
    Mapper<com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv, AppSettingsListItem>, NullableMapper<com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv, AppSettingsListItem> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv) = AppSettingsListItem(
        id = input.id!!,
        paramName = input.paramName,
        paramValue = input.paramValue,
        paramFullName = input.paramFullName
    )

    override fun nullableMap(input: com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv?) = input?.let { map(it) }
}