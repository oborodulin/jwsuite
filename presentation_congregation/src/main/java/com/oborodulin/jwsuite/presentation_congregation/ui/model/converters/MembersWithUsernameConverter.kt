package com.oborodulin.jwsuite.presentation_congregation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.member.GetMembersWithUsernameUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersWithUsernameUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.MembersWithUsernameToMembersWithUsernameUiMapper

class MembersWithUsernameConverter(private val mapper: MembersWithUsernameToMembersWithUsernameUiMapper) :
    CommonResultConverter<GetMembersWithUsernameUseCase.Response, MembersWithUsernameUi>() {
    override fun convertSuccess(data: GetMembersWithUsernameUseCase.Response) =
        mapper.map(data.membersWithUsername)
}