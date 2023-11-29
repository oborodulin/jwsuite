package com.oborodulin.jwsuite.presentation_congregation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.role.GetRolesUseCase
import com.oborodulin.jwsuite.presentation.ui.model.RolesListItem
import com.oborodulin.jwsuite.presentation.ui.model.mappers.RolesListToRolesListItemMapper

class RolesListConverter(private val mapper: RolesListToRolesListItemMapper) :
    CommonResultConverter<GetRolesUseCase.Response, List<RolesListItem>>() {
    override fun convertSuccess(data: GetRolesUseCase.Response) = mapper.map(data.roles)
}