package com.oborodulin.jwsuite.presentation_congregation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi

data class MembersWithUsernameUi(
    val username: String? = null,
    val members: List<MembersListItem> = emptyList()
) : ModelUi()