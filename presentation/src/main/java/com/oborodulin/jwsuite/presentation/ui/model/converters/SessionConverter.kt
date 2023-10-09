package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.session.GetSessionUseCase
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.model.mappers.SessionToSessionUiMapper

class SessionConverter(private val mapper: SessionToSessionUiMapper) :
    CommonResultConverter<GetSessionUseCase.Response, SessionUi>() {
    override fun convertSuccess(data: GetSessionUseCase.Response) = mapper.map(data.session)
}