package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingsUseCase
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListToAppSettingsListItemMapper

class AppSettingListConverter(private val mapper: AppSettingsListToAppSettingsListItemMapper) :
    CommonResultConverter<GetAppSettingsUseCase.Response, List<AppSettingsListItem>>() {
    override fun convertSuccess(data: GetAppSettingsUseCase.Response) = mapper.map(data.settings)
}