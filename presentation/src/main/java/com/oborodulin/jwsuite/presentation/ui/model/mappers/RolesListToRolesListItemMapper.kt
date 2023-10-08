package com.oborodulin.jwsuite.presentation.ui.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.congregation.Role
import com.oborodulin.jwsuite.presentation.ui.model.RolesListItem

class RolesListToRolesListItemMapper(mapper: RoleToRolesListItemMapper) :
    ListMapperImpl<Role, RolesListItem>(mapper)