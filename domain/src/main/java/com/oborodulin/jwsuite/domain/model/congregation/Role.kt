package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.MemberRoleType
import kotlinx.serialization.Serializable

@Serializable
data class Role(
    val roleType: MemberRoleType = MemberRoleType.USER,
    val roleName: String = ""
) : DomainModel()
