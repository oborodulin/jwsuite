package com.oborodulin.jwsuite.presentation.ui.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.appsetting.AppSetting
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem

class AppSettingsListToAppSettingsListItemMapper(mapper: AppSettingToAppSettingsListItemMapper) :
    ListMapperImpl<AppSetting, AppSettingsListItem>(mapper)