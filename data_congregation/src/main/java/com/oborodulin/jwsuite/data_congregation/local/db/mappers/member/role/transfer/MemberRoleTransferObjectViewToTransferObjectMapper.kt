package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer.TransferObjectEntityToTransferObjectMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView
import com.oborodulin.jwsuite.domain.model.congregation.TransferObject

class MemberRoleTransferObjectViewToTransferObjectMapper(private val mapper: TransferObjectEntityToTransferObjectMapper) :
    NullableMapper<MemberRoleTransferObjectView, TransferObject>,
    Mapper<MemberRoleTransferObjectView, TransferObject> {
    override fun map(input: MemberRoleTransferObjectView) =
        mapper.map(input.roleTransferObject.transferObject)

    override fun nullableMap(input: MemberRoleTransferObjectView?) = input?.let { map(it) }
}