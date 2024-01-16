package com.oborodulin.jwsuite.data.local.db.mappers.csv.member.role

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView

class MemberRoleViewListToRolesListMapper(mapper: MemberRoleViewToRoleMapper) :
    ListMapperImpl<MemberRoleView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.RoleCsv>(mapper)