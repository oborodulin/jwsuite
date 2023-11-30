package com.oborodulin.jwsuite.presentation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class AppSettingsUiModel(
    val settings: List<AppSettingsListItem> = emptyList(),
    val username: String,
    val roles: List<MemberRolesListItem> = emptyList(),
    val transferObjects: List<RoleTransferObjectsListItem> = emptyList(),
    val appVersionName: String,
    val frameworkVersion: String,
    val sqliteVersion: String,
    val dbVersion: String
) : ModelUi()