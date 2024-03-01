package com.oborodulin.jwsuite.data_congregation.local.db.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.domain.model.congregation.Member

class MemberEntityToMemberMapper : NullableMapper<MemberEntity, Member>,
    Mapper<MemberEntity, Member> {
    override fun map(input: MemberEntity) = Member(
        memberNum = input.memberNum,
        memberName = input.memberName,
        surname = input.surname,
        patronymic = input.patronymic,
        pseudonym = input.pseudonym,
        phoneNumber = input.phoneNumber,
        dateOfBirth = input.dateOfBirth,
        dateOfBaptism = input.dateOfBaptism,
        loginExpiredDate = input.loginExpiredDate
    ).also { it.id = input.memberId }

    override fun nullableMap(input: MemberEntity?) = input?.let { map(it) }
}