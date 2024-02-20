package com.oborodulin.jwsuite.domain.model.appsetting

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.model.congregation.RoleTransferObject

data class DataManagementSettings(
    val settings: List<AppSetting> = emptyList(),
    val roleTransferObjects: List<RoleTransferObject> = emptyList()
) : ModelUi()