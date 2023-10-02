package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.MemberRoleType

data class Role(
    val roleType: MemberRoleType = MemberRoleType.USER,
    val roleName: String
) : DomainModel()
