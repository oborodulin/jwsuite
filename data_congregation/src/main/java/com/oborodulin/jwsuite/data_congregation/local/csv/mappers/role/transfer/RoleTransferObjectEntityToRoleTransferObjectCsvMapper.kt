package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleTransferObjectEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv

class RoleTransferObjectEntityToRoleTransferObjectCsvMapper :
    Mapper<RoleTransferObjectEntity, RoleTransferObjectCsv> {
    override fun map(input: RoleTransferObjectEntity) = RoleTransferObjectCsv(
        roleTransferObjectId = input.roleTransferObjectId,
        isPersonalData = input.isPersonalData,
        rtoRolesId = input.rtoRolesId,
        rtoTransferObjectsId = input.rtoTransferObjectsId
    )
}