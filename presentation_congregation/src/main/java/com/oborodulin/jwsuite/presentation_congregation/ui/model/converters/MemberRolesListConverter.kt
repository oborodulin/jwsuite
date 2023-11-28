package com.oborodulin.jwsuite.presentation_congregation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.member.role.GetMemberRolesUseCase
import com.oborodulin.jwsuite.presentation.ui.model.MemberRolesListItem
import com.oborodulin.jwsuite.presentation.ui.model.mappers.MemberRolesListToMemberRolesListItemMapper

class MemberRolesListConverter(private val mapper: MemberRolesListToMemberRolesListItemMapper) :
    CommonResultConverter<GetMemberRolesUseCase.Response, List<MemberRolesListItem>>() {
    override fun convertSuccess(data: GetMemberRolesUseCase.Response) = mapper.map(data.memberRoles)
}