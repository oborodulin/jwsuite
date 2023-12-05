package com.oborodulin.jwsuite.presentation_congregation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.group.SaveGroupUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.GroupUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.group.GroupToGroupUiMapper

class SaveGroupConverter(private val mapper: GroupToGroupUiMapper) :
    CommonResultConverter<SaveGroupUseCase.Response, GroupUi>() {
    override fun convertSuccess(data: SaveGroupUseCase.Response) = mapper.map(data.group)
}