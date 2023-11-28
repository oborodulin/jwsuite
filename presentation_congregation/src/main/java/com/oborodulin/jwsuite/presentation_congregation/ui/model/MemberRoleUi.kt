package com.oborodulin.jwsuite.presentation_congregation.ui.model

import com.oborodulin.home.common.ui.model.ModelUi
import java.time.OffsetDateTime

data class MemberRoleUi(
    val member: MemberUi = MemberUi(),
    val role: RoleUi = RoleUi(),
    val roleExpiredDate: OffsetDateTime? = null
) : ModelUi()