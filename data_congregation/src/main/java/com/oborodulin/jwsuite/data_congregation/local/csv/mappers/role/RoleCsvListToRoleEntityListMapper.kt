package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.role

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv

class RoleCsvListToRoleEntityListMapper(mapper: RoleCsvToRoleEntityMapper) :
    ListMapperImpl<RoleCsv, RoleEntity>(mapper)