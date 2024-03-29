package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.types.MemberType
import java.time.OffsetDateTime
import java.util.UUID

data class MemberMovement(
    val memberId: UUID,
    val memberType: MemberType = MemberType.PREACHER,
    val movementDate: OffsetDateTime = OffsetDateTime.now()
) : DomainModel()
