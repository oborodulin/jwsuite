package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class Group(
    val congregationId: UUID,
    val groupNum: Int,
    val members: List<Member> = emptyList()
) : DomainModel()
