package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.RoleTransferObjectView
import com.oborodulin.jwsuite.domain.model.congregation.RoleTransferObject

class RoleTransferObjectViewListToRoleTransferObjectsListMapper(mapper: RoleTransferObjectViewToRoleTransferObjectMapper) :
    ListMapperImpl<RoleTransferObjectView, RoleTransferObject>(mapper)