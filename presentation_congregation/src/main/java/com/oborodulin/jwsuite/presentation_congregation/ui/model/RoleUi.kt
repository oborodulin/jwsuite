package com.oborodulin.jwsuite.presentation_congregation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.util.MemberRoleType

data class RoleUi(
    val roleType: MemberRoleType = MemberRoleType.USER,
    val roleName: String = ""
) : ModelUi()