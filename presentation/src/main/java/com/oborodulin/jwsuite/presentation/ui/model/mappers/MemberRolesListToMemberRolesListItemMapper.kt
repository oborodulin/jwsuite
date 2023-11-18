package com.oborodulin.jwsuite.presentation.ui.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.congregation.MemberRole
import com.oborodulin.jwsuite.presentation.ui.model.MemberRolesListItem

class MemberRolesListToMemberRolesListItemMapper(mapper: MemberRoleToMemberRolesListItemMapper) :
    ListMapperImpl<MemberRole, MemberRolesListItem>(mapper)