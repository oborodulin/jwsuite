package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel
import java.time.OffsetDateTime
import java.util.UUID

data class MemberCongregation(
    val memberId: UUID,
    val congregationId: UUID,
    val activityDate: OffsetDateTime = OffsetDateTime.now()
) : DomainModel()
