package com.oborodulin.jwsuite.domain.model.congregation

import com.oborodulin.home.common.domain.model.DomainModel
import java.util.UUID

data class RoleTransferObject(
    val memberId: UUID,
    val roleId: UUID,
    val isPersonalData: Boolean,
    val transferObject: TransferObject
) : DomainModel()
