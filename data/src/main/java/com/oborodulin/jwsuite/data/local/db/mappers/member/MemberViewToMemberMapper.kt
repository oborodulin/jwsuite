package com.oborodulin.jwsuite.data.local.db.mappers.member

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.mappers.group.GroupViewToGroupMapper
import com.oborodulin.jwsuite.data.local.db.views.MemberView
import com.oborodulin.jwsuite.domain.model.Member

class MemberViewToMemberMapper(private val groupMapper: GroupViewToGroupMapper) :
    Mapper<MemberView, Member> {
    override fun map(input: MemberView): Member {
        val member = Member(
            group = groupMapper.map(input.group),
            memberNum = input.member.memberNum,
            memberName = input.member.memberName,
            surname = input.member.surname,
            patronymic = input.member.patronymic,
            pseudonym = input.member.pseudonym,
            phoneNumber = input.member.phoneNumber,
            memberType = input.member.memberType,
            dateOfBirth = input.member.dateOfBirth,
            dateOfBaptism = input.member.dateOfBaptism,
            inactiveDate = input.member.inactiveDate
        )
        member.id = input.member.memberId
        return member
    }
}