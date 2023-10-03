package com.oborodulin.jwsuite.domain.model

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class MemberMovement(
    val memberId: UUID,
    val movement: Movement
) : DomainModel()
