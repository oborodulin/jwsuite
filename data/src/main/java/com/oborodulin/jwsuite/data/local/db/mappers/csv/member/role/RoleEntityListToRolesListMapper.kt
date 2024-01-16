package com.oborodulin.jwsuite.data.local.db.mappers.csv.member.role

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity

class RoleEntityListToRolesListMapper(mapper: RoleEntityToRoleMapper) :
    ListMapperImpl<RoleEntity, com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv>(mapper)