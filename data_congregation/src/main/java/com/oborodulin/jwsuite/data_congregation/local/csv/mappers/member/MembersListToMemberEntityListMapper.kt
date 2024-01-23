package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity

class MembersListToMemberEntityListMapper(mapper: MemberToMemberEntityMapper) :
    ListMapperImpl<com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv, MemberEntity>(mapper)