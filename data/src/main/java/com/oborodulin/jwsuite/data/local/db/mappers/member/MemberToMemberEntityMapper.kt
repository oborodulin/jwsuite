package com.oborodulin.jwsuite.data.local.db.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.domain.model.Member
import java.util.UUID

class MemberToMemberEntityMapper : Mapper<Member, MemberEntity> {
    override fun map(input: Member) = MemberEntity(
        memberId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        groupsId = input.group.id!!,
        memberNum = input.memberNum,
        memberName = input.memberName,
        surname = input.surname,
        patronymic = input.patronymic,
        pseudonym = input.pseudonym,
        phoneNumber = input.phoneNumber,
        memberType = input.memberType,
        dateOfBirth = input.dateOfBirth,
        dateOfBaptism = input.dateOfBaptism,
        inactiveDate = input.inactiveDate
    )
}