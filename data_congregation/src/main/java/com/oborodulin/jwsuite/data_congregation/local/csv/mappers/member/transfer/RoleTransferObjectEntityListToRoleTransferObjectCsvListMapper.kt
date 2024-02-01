package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleTransferObjectCsv

class RoleTransferObjectEntityListToRoleTransferObjectCsvListMapper(mapper: RoleTransferObjectEntityToRoleTransferObjectCsvMapper) :
    ListMapperImpl<RoleTransferObjectEntity, RoleTransferObjectCsv>(mapper)