package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetAppSettingsUseCase
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsUiModel
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListToAppSettingsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.MemberRolesListToMemberRolesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.transfer.RoleTransferObjectsListToRoleTransferObjectsListItemMapper

class AppSettingUiModelConverter(
    private val settingsMapper: AppSettingsListToAppSettingsListItemMapper,
    private val rolesMapper: MemberRolesListToMemberRolesListItemMapper,
    private val transferObjectsMapper: RoleTransferObjectsListToRoleTransferObjectsListItemMapper
) :
    CommonResultConverter<GetAppSettingsUseCase.Response, AppSettingsUiModel>() {
    override fun convertSuccess(data: GetAppSettingsUseCase.Response) =
        AppSettingsUiModel(
            settings = settingsMapper.map(data.appSettingsWithSession.settings),
            username = data.appSettingsWithSession.username,
            roles = rolesMapper.map(data.appSettingsWithSession.roles),
            transferObjects = transferObjectsMapper.map(data.appSettingsWithSession.transferObjects),
            versionName = data.appSettingsWithSession.versionName
        )
}