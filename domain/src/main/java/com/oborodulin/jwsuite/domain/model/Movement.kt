package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import com.oborodulin.jwsuite.domain.util.MemberType
import java.time.OffsetDateTime

data class Movement(
    val memberType: MemberType = MemberType.PREACHER,
    val movementDate: OffsetDateTime
) : DomainModel()
