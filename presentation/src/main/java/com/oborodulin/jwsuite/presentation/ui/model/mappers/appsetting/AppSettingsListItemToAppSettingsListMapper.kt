package com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.appsetting.AppSetting
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem

class AppSettingsListItemToAppSettingsListMapper(mapper: AppSettingsListItemToAppSettingMapper) :
    ListMapperImpl<AppSettingsListItem, AppSetting>(mapper)