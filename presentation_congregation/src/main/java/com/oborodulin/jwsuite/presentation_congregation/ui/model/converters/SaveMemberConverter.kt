package com.oborodulin.jwsuite.presentation_congregation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.member.SaveMemberUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.MemberToMemberUiMapper

class SaveMemberConverter(private val mapper: MemberToMemberUiMapper) :
    CommonResultConverter<SaveMemberUseCase.Response, MemberUi>() {
    override fun convertSuccess(data: SaveMemberUseCase.Response) = mapper.map(data.member)
}