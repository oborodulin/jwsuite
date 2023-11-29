package com.oborodulin.jwsuite.presentation_congregation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.member.role.GetMemberRoleUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberRoleUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.MemberRoleToMemberRoleUiMapper

class MemberRoleConverter(private val mapper: MemberRoleToMemberRoleUiMapper) :
    CommonResultConverter<GetMemberRoleUseCase.Response, MemberRoleUi>() {
    override fun convertSuccess(data: GetMemberRoleUseCase.Response) = mapper.map(data.memberRole)
}