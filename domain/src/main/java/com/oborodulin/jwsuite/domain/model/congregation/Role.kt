package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import kotlinx.serialization.Serializable

@Serializable
data class Role(
    //@Serializable(with = MemberRoleTypeSerializer::class)
    val roleType: MemberRoleType = MemberRoleType.USER,
    val roleName: String = ""
) : DomainModel()
