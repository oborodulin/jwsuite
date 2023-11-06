package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.jwsuite.presentation.ui.model.mappers.SessionToSessionUiMapper

class LoginSessionConverter(private val mapper: SessionToSessionUiMapper) {}/*:
    CommonResultConverter<LoginUseCase.Response, SessionUi>() {
    override fun convertSuccess(data: LoginUseCase.Response) = mapper.map(data.session)
}*/