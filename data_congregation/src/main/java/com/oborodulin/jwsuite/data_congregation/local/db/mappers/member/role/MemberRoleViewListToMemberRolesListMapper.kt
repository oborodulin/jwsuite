package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member.role

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView
import com.oborodulin.jwsuite.domain.model.MemberRole

class MemberRoleViewListToMemberRolesListMapper(mapper: MemberRoleViewToMemberRoleMapper) :
    ListMapperImpl<MemberRoleView, MemberRole>(mapper)