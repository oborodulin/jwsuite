package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView

class MemberRoleTransferObjectViewListToMemberRoleTransferObjectsListMapper(mapper: MemberRoleTransferObjectViewToMemberRoleTransferObjectMapper) :
    ListMapperImpl<MemberRoleTransferObjectView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleTransferObject>(mapper)