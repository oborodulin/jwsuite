package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.role.transfer

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.congregation.RoleTransferObject
import com.oborodulin.jwsuite.presentation_congregation.ui.model.RoleTransferObjectsListItem

class RoleTransferObjectsListToRoleTransferObjectsListItemMapper(mapper: RoleTransferObjectToRoleTransferObjectsListItemMapper) :
    ListMapperImpl<RoleTransferObject, RoleTransferObjectsListItem>(mapper)