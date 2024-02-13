package com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetDashboardSettingsUseCase
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DashboardSettingsUiModel
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.role.MemberRolesListToMemberRolesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListToAppSettingsListItemMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.role.transfer.RoleTransferObjectsListToRoleTransferObjectsListItemMapper

class DashboardSettingUiModelConverter(
    private val settingsMapper: AppSettingsListToAppSettingsListItemMapper,
    private val rolesMapper: MemberRolesListToMemberRolesListItemMapper,
    private val transferObjectsMapper: RoleTransferObjectsListToRoleTransferObjectsListItemMapper
) :
    CommonResultConverter<GetDashboardSettingsUseCase.Response, DashboardSettingsUiModel>() {
    override fun convertSuccess(data: GetDashboardSettingsUseCase.Response) =
        DashboardSettingsUiModel(
            settings = settingsMapper.map(data.appSettingsWithSession.settings),
            username = data.appSettingsWithSession.username,
            roles = rolesMapper.map(data.appSettingsWithSession.roles),
            transferObjects = transferObjectsMapper.map(data.appSettingsWithSession.roleTransferObjects),
            appVersionName = data.appSettingsWithSession.appVersionName,
            frameworkVersion = data.appSettingsWithSession.frameworkVersion,
            sqliteVersion = data.appSettingsWithSession.sqliteVersion,
            dbVersion = data.appSettingsWithSession.dbVersion
        )
}