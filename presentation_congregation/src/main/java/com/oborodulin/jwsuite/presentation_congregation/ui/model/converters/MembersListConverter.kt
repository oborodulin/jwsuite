package com.oborodulin.jwsuite.presentation_congregation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.member.GetMembersUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.MembersListToMembersListItemMapper

class MembersListConverter(private val mapper: MembersListToMembersListItemMapper) :
    CommonResultConverter<GetMembersUseCase.Response, List<MembersListItem>>() {
    override fun convertSuccess(data: GetMembersUseCase.Response) = mapper.map(data.members)
}