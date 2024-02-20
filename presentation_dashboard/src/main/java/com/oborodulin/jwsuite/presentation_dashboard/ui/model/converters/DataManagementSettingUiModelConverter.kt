package com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.appsetting.GetDataManagementSettingsUseCase
import com.oborodulin.jwsuite.presentation.ui.model.mappers.appsetting.AppSettingsListToAppSettingsListItemMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.role.transfer.RoleTransferObjectsListToRoleTransferObjectsListItemMapper
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DataManagementSettingsUiModel

class DataManagementSettingUiModelConverter(
    private val settingsMapper: AppSettingsListToAppSettingsListItemMapper,
    private val transferObjectsMapper: RoleTransferObjectsListToRoleTransferObjectsListItemMapper
) : CommonResultConverter<GetDataManagementSettingsUseCase.Response, DataManagementSettingsUiModel>() {
    override fun convertSuccess(data: GetDataManagementSettingsUseCase.Response) =
        DataManagementSettingsUiModel(
            settings = settingsMapper.map(data.dataManagementSettings.settings),
            transferObjects = transferObjectsMapper.map(data.dataManagementSettings.roleTransferObjects)
        )
}