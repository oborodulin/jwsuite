package com.oborodulin.jwsuite.presentation_dashboard.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.RoleTransferObjectsListItem

data class DataManagementSettingsUiModel(
    val settings: List<AppSettingsListItem> = emptyList(),
    val transferObjects: List<RoleTransferObjectsListItem> = emptyList()
) : ModelUi()