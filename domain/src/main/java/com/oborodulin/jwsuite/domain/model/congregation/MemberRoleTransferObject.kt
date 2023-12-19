package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class MemberRoleTransferObject(
    val memberId: UUID,
    val roleTransferObject: RoleTransferObject
) : DomainModel()
