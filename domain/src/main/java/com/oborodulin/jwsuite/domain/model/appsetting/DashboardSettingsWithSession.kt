package com.oborodulin.jwsuite.domain.model.appsetting

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole

data class DashboardSettingsWithSession(
    val settings: List<AppSetting> = emptyList(),
    val username: String,
    val roles: List<MemberRole> = emptyList(),
    val appVersionName: String,
    val frameworkVersion: String,
    val sqliteVersion: String,
    val dbVersion: String
) : ModelUi()