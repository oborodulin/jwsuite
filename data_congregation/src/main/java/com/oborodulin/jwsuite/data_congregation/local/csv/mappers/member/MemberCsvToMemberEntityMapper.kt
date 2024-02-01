package com.oborodulin.jwsuite.data_congregation.local.csv.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.MemberCsv

class MemberCsvToMemberEntityMapper : Mapper<MemberCsv, MemberEntity> {
    override fun map(input: MemberCsv) = MemberEntity(
        memberId = input.memberId,
        memberNum = input.memberNum,
        memberName = input.memberName,
        surname = input.surname,
        patronymic = input.patronymic,
        pseudonym = input.pseudonym,
        phoneNumber = input.phoneNumber,
        dateOfBirth = input.dateOfBirth,
        dateOfBaptism = input.dateOfBaptism,
        loginExpiredDate = input.loginExpiredDate,
        mGroupsId = input.mGroupsId
    )
}