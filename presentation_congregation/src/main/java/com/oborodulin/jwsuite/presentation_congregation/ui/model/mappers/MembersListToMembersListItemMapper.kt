package com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.Member
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersListItem

class MembersListToMembersListItemMapper(mapper: MemberToMembersListItemMapper) :
    ListMapperImpl<Member, MembersListItem>(mapper)