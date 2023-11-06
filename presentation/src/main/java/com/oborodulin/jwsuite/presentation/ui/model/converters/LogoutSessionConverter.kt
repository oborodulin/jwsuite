package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.session.LogoutUseCase
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.model.mappers.SessionToSessionUiMapper

class LogoutSessionConverter(private val mapper: SessionToSessionUiMapper) {}/*:
    CommonResultConverter<LogoutUseCase.Response, SessionUi>() {
    override fun convertSuccess(data: LogoutUseCase.Response) = mapper.map(data.session)
}*/