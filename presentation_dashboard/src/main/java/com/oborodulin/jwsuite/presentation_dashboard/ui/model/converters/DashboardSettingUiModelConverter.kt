package com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetDashboardSettingsUseCase
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListToAppSettingsListItemMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.role.MemberRolesListToMemberRolesListItemMapper
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DashboardSettingsUiModel

class DashboardSettingUiModelConverter(
    private val settingsMapper: AppSettingsListToAppSettingsListItemMapper,
    private val rolesMapper: MemberRolesListToMemberRolesListItemMapper
) :
    CommonResultConverter<GetDashboardSettingsUseCase.Response, DashboardSettingsUiModel>() {
    override fun convertSuccess(data: GetDashboardSettingsUseCase.Response) =
        DashboardSettingsUiModel(
            settings = settingsMapper.map(data.dashboardSettingsWithSession.settings),
            username = data.dashboardSettingsWithSession.username,
            roles = rolesMapper.map(data.dashboardSettingsWithSession.roles),
            appVersionName = data.dashboardSettingsWithSession.appVersionName,
            frameworkVersion = data.dashboardSettingsWithSession.frameworkVersion,
            sqliteVersion = data.dashboardSettingsWithSession.sqliteVersion,
            dbVersion = data.dashboardSettingsWithSession.dbVersion
        )
}