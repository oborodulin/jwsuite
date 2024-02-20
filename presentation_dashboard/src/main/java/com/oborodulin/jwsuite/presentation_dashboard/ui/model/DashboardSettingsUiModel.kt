package com.oborodulin.jwsuite.presentation_dashboard.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberRolesListItem

data class DashboardSettingsUiModel(
    val settings: List<AppSettingsListItem> = emptyList(),
    val username: String,
    val roles: List<MemberRolesListItem> = emptyList(),
    val appVersionName: String,
    val frameworkVersion: String,
    val sqliteVersion: String,
    val dbVersion: String
) : ModelUi()