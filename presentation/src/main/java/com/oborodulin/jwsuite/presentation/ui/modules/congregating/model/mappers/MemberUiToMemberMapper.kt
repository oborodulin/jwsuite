package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.Member
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MemberUi

class MemberUiToMemberMapper(private val groupUiMapper: GroupUiToGroupMapper) :
    Mapper<MemberUi, Member> {
    override fun map(input: MemberUi): Member {
        val member = Member(
            group = groupUiMapper.map(input.group),
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
        member.id = input.id
        return member
    }
}