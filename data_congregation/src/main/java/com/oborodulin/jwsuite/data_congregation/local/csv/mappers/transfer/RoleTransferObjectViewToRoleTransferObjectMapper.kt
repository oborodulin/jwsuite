package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.RoleTransferObjectView

class RoleTransferObjectViewToRoleTransferObjectMapper(private val mapper: TransferObjectEntityToTransferObjectMapper) :
    NullableMapper<RoleTransferObjectView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv>,
    Mapper<RoleTransferObjectView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv> {
    override fun map(input: RoleTransferObjectView): com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv {
        val roleTransferObjectCsv =
            com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv(
                roleId = input.roleTransferObject.rtoRolesId,
                isPersonalData = input.roleTransferObject.isPersonalData,
                transferObjectCsv = mapper.map(input.transferObject)
            )
        roleTransferObjectCsv.id = input.roleTransferObject.roleTransferObjectId
        return roleTransferObjectCsv
    }

    override fun nullableMap(input: RoleTransferObjectView?) = input?.let { map(it) }
}