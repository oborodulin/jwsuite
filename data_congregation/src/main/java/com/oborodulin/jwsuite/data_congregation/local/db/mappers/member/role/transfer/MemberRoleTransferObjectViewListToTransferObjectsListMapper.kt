package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView
import com.oborodulin.jwsuite.domain.model.congregation.TransferObject

class MemberRoleTransferObjectViewListToTransferObjectsListMapper(mapper: MemberRoleTransferObjectViewToTransferObjectMapper) :
    ListMapperImpl<MemberRoleTransferObjectView, TransferObject>(mapper)