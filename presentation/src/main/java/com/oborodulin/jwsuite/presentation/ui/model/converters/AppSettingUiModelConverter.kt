package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingsUseCase
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsUiModel
import com.oborodulin.jwsuite.presentation.ui.model.mappers.AppSettingsListToAppSettingsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.RolesListToRolesListItemMapper

class AppSettingUiModelConverter(
    private val settingsMapper: AppSettingsListToAppSettingsListItemMapper,
    private val rolesMapper: RolesListToRolesListItemMapper
) :
    CommonResultConverter<GetAppSettingsUseCase.Response, AppSettingsUiModel>() {
    override fun convertSuccess(data: GetAppSettingsUseCase.Response) =
        AppSettingsUiModel(
            settings = settingsMapper.map(data.appSettingsWithSession.settings),
            username = data.appSettingsWithSession.username,
            roles = rolesMapper.map(data.appSettingsWithSession.roles),
            versionName = data.appSettingsWithSession.versionName
        )
}