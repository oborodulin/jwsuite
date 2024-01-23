package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView

class MemberRoleTransferObjectViewListToTransferObjectsListMapper(mapper: MemberRoleTransferObjectViewToTransferObjectMapper) :
    ListMapperImpl<MemberRoleTransferObjectView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.TransferObjectCsv>(mapper)