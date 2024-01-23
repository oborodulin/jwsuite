package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.role

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity

class RoleEntityListToRolesListMapper(mapper: RoleEntityToRoleMapper) :
    ListMapperImpl<RoleEntity, com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv>(mapper)