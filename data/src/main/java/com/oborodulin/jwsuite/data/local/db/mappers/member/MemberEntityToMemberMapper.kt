package com.oborodulin.jwsuite.data.local.db.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.domain.model.Member

class MemberEntityToMemberMapper : Mapper<MemberEntity, Member> {
    override fun map(input: MemberEntity): Member {
        val member = Member(
            groupId = input.groupsId,
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
        member.id = input.memberId
        return member
    }
}