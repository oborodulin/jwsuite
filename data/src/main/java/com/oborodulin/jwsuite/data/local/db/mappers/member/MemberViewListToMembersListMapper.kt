package com.oborodulin.jwsuite.data.local.db.mappers.member

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.MemberView
import com.oborodulin.jwsuite.domain.model.Member

class MemberViewListToMembersListMapper(mapper: MemberViewToMemberMapper) :
    ListMapperImpl<MemberView, Member>(mapper)