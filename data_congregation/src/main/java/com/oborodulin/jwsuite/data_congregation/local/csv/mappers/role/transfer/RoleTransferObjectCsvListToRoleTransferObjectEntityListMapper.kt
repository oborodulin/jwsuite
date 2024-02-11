package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleTransferObjectEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv

class RoleTransferObjectCsvListToRoleTransferObjectEntityListMapper(mapper: RoleTransferObjectCsvToRoleTransferObjectEntityMapper) :
    ListMapperImpl<RoleTransferObjectCsv, RoleTransferObjectEntity>(mapper)