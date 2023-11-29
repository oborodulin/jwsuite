package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel
import java.time.OffsetDateTime

data class MemberRole(
    val member: Member,
    val role: Role,
    val roleExpiredDate: OffsetDateTime? = null
) : DomainModel()
