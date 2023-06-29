package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.member.GetMembersUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MembersListItem
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.MembersListToMembersListItemMapper

class MembersListConverter(private val mapper: MembersListToMembersListItemMapper) :
    CommonResultConverter<GetMembersUseCase.Response, List<MembersListItem>>() {
    override fun convertSuccess(data: GetMembersUseCase.Response) = mapper.map(data.members)
}