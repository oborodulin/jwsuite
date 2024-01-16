package com.oborodulin.jwsuite.data.local.db.mappers.csv.transfer

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView

class MemberRoleTransferObjectViewToTransferObjectMapper(private val mapper: TransferObjectEntityToTransferObjectMapper) :
    NullableMapper<MemberRoleTransferObjectView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv>,
    Mapper<MemberRoleTransferObjectView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv> {
    override fun map(input: MemberRoleTransferObjectView) =
        mapper.map(input.roleTransferObject.transferObject)

    override fun nullableMap(input: MemberRoleTransferObjectView?) = input?.let { map(it) }
}