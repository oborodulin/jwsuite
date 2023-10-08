package com.oborodulin.jwsuite.presentation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.model.congregation.Role

data class SessionUi(
    val isSigned: Boolean = false,
    val isLogged: Boolean = false,
    val roles: List<RolesListItem> = emptyList(),
    val currentNavRoute: String? = null
) : ModelUi()
