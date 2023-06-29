package com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.group.GetGroupUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.GroupUi
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.GroupToGroupUiMapper

class GroupConverter(private val mapper: GroupToGroupUiMapper) :
    CommonResultConverter<GetGroupUseCase.Response, GroupUi>() {
    override fun convertSuccess(data: GetGroupUseCase.Response) = mapper.map(data.group)
}