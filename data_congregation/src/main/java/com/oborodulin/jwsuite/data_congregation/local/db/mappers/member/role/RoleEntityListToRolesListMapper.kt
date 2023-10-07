package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity
import com.oborodulin.jwsuite.domain.model.congregation.Role

class RoleEntityListToRolesListMapper(mapper: RoleEntityToRoleMapper) :
    ListMapperImpl<RoleEntity, Role>(mapper)