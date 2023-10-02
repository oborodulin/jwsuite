package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class MemberRole(
    val memberId: UUID,
    val role: Role
) : DomainModel()
