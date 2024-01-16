package com.oborodulin.jwsuite.data.local.db.mappers.csv.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import java.util.UUID

class MemberToMemberEntityMapper : Mapper<com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv, MemberEntity> {
    override fun map(input: com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv) = MemberEntity(
        memberId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        mGroupsId = input.groupCsv?.id,
        memberNum = input.memberNum,
        memberName = input.memberName,
        surname = input.surname,
        patronymic = input.patronymic,
        pseudonym = input.pseudonym,
        phoneNumber = input.phoneNumber,
        dateOfBirth = input.dateOfBirth,
        dateOfBaptism = input.dateOfBaptism
    )
}