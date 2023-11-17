package com.oborodulin.jwsuite.domain.model.appsetting

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.model.congregation.Role

data class AppSettingsWithSession(
    val settings: List<AppSetting> = emptyList(),
    val username: String,
    val roles: List<Role> = emptyList(),
    val versionName: String
) : ModelUi()