package com.oborodulin.jwsuite.data_congregation.local.db.mappers.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView
import com.oborodulin.jwsuite.domain.model.congregation.RoleTransferObject

class MemberRoleTransferObjectViewListToRoleTransferObjectsListMapper(mapper: MemberRoleTransferObjectViewToRoleTransferObjectMapper) :
    ListMapperImpl<MemberRoleTransferObjectView, RoleTransferObject>(mapper)