package com.oborodulin.jwsuite.data.local.db.mappers.csv.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.RoleTransferObjectView

class RoleTransferObjectViewListToRoleTransferObjectsListMapper(mapper: RoleTransferObjectViewToRoleTransferObjectMapper) :
    ListMapperImpl<RoleTransferObjectView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv>(mapper)