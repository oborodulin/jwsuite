package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv

class MemberCsvListToMemberEntityListMapper(mapper: MemberCsvToMemberEntityMapper) :
    ListMapperImpl<MemberCsv, MemberEntity>(mapper)