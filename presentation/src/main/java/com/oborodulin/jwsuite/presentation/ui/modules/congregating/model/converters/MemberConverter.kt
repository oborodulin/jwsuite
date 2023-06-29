package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.member.GetMemberUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MemberUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.MemberToMemberUiMapper

class MemberConverter(private val mapper: MemberToMemberUiMapper) :
    CommonResultConverter<GetMemberUseCase.Response, MemberUi>() {
    override fun convertSuccess(data: GetMemberUseCase.Response) = mapper.map(data.member)
}