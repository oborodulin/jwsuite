package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.Member
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MembersListItem

class MembersListToMembersListItemMapper(mapper: MemberToMembersListItemMapper) :
    ListMapperImpl<Member, MembersListItem>(mapper)