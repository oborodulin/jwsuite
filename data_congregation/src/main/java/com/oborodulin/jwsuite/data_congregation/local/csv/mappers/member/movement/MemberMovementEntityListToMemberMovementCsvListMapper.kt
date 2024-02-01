package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member.movement

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberMovementCsv

class MemberMovementEntityListToMemberMovementCsvListMapper(mapper: MemberMovementEntityToMemberMovementCsvMapper) :
    ListMapperImpl<MemberMovementEntity, MemberMovementCsv>(mapper)