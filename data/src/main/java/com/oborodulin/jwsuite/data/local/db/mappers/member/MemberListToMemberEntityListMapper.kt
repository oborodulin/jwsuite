package com.oborodulin.jwsuite.data.local.db.mappers.member

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.domain.model.Member

class MemberListToMemberEntityListMapper(mapper: MemberToMemberEntityMapper) :
    ListMapperImpl<Member, MemberEntity>(mapper)