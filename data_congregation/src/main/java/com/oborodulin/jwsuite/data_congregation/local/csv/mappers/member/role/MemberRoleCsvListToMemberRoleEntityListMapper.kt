package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.role

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberRoleCsv

class MemberRoleCsvListToMemberRoleEntityListMapper(mapper: MemberRoleCsvToMemberRoleEntityMapper) :
    ListMapperImpl<MemberRoleCsv, MemberRoleEntity>(mapper)