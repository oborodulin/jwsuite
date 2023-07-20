package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.home.common.mapping.NullableMapper
import com.oborodulin.jwsuite.domain.model.Member
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MemberUi

class MemberToMemberUiMapper(private val groupMapper: GroupToGroupUiMapper) :
    NullableMapper<Member, MemberUi>,
    Mapper<Member, MemberUi> {
    override fun map(input: Member): MemberUi {
        val memberUi = MemberUi(
            group = groupMapper.map(input.group),
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
        memberUi.id = input.id
        return memberUi
    }

    override fun nullableMap(input: Member?) = input?.let { map(it) }
}