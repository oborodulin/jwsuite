package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.session.SignupUseCase
import com.oborodulin.jwsuite.presentation.ui.model.SessionUi
import com.oborodulin.jwsuite.presentation.ui.model.mappers.SessionToSessionUiMapper

class SignupSessionConverter(private val mapper: SessionToSessionUiMapper) {}/* :
    CommonResultConverter<SignupUseCase.Response, SessionUi>() {
    override fun convertSuccess(data: SignupUseCase.Response) = mapper.map(data.session)
}*/