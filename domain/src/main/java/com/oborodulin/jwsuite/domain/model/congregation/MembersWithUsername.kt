package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel

data class MembersWithUsername(
    val username: String? = null,
    val members: List<Member> = emptyList()
) : DomainModel()
