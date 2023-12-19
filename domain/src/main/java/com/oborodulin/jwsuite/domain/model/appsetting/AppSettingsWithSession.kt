package com.oborodulin.jwsuite.domain.model.appsetting

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.domain.model.congregation.RoleTransferObject

data class AppSettingsWithSession(
    val settings: List<AppSetting> = emptyList(),
    val username: String,
    val roles: List<MemberRole> = emptyList(),
    val roleTransferObjects: List<RoleTransferObject> = emptyList(),
    val appVersionName: String,
    val frameworkVersion: String,
    val sqliteVersion: String,
    val dbVersion: String
) : ModelUi()