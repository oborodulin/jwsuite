package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import com.oborodulin.jwsuite.domain.model.Member

class MemberViewListToMembersListMapper(mapper: MemberViewToMemberMapper) :
    ListMapperImpl<MemberView, Member>(mapper)