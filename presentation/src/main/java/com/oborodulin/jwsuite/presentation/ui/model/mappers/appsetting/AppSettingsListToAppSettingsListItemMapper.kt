package com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem

class AppSettingsListToAppSettingsListItemMapper(mapper: AppSettingToAppSettingsListItemMapper) :
    ListMapperImpl<com.oborodulin.jwsuite.domain.services.csv.model.appsetting.AppSettingCsv, AppSettingsListItem>(mapper)