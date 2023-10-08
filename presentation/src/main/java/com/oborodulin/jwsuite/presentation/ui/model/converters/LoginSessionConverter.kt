package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.session.LoginUseCase
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.model.mappers.SessionToSessionUiMapper

class LoginSessionConverter(private val mapper: SessionToSessionUiMapper) :
    CommonResultConverter<LoginUseCase.Response, SessionUi>() {
    override fun convertSuccess(data: LoginUseCase.Response) = mapper.map(data.session)
}