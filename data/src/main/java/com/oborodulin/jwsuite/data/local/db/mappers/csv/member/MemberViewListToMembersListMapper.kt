package com.oborodulin.jwsuite.data.local.db.mappers.csv.member

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView

class MemberViewListToMembersListMapper(mapper: MemberViewToMemberMapper) :
    ListMapperImpl<MemberView, com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv>(mapper)