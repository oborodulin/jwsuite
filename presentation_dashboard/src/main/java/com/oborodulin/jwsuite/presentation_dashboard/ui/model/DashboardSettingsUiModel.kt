package com.oborodulin.jwsuite.presentation_dashboard.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem

data class DashboardSettingsUiModel(
    val settings: List<AppSettingsListItem> = emptyList(),
    val username: String,
    val roles: List<com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberRolesListItem> = emptyList(),
    val transferObjects: List<com.oborodulin.jwsuite.presentation_congregation.ui.model.RoleTransferObjectsListItem> = emptyList(),
    val appVersionName: String,
    val frameworkVersion: String,
    val sqliteVersion: String,
    val dbVersion: String
) : ModelUi()