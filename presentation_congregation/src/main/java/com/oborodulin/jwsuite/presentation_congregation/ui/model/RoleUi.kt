package com.oborodulin.jwsuite.presentation_congregation.ui.model

import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.domain.types.MemberRoleType

data class RoleUi(
    val roleType: MemberRoleType = MemberRoleType.USER,
    val roleName: String = ""
) : ModelUi()

fun ListItemModel?.toRoleUi() =
    RoleUi(roleName = this?.headline.orEmpty()).also { it.id = this?.itemId }