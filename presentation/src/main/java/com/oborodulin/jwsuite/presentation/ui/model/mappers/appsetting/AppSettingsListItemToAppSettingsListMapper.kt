package com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem

class AppSettingsListItemToAppSettingsListMapper(mapper: AppSettingsListItemToAppSettingMapper) :
    ListMapperImpl<AppSettingsListItem, com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv>(mapper)