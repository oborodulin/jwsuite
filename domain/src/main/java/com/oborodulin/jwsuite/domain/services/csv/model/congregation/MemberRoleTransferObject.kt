package com.oborodulin.jwsuite.domain.services.csv.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class MemberRoleTransferObject(
    val memberId: UUID,
    val roleTransferObjectCsv: com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv
) : DomainModel()
