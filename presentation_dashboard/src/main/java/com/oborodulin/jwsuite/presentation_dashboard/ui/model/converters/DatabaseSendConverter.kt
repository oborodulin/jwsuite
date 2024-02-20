package com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.db.SendDataUseCase
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DatabaseUiModel
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.mappers.ObjectsTransferStateToDatabaseUiModelMapper

class DatabaseSendConverter(private val mapper: ObjectsTransferStateToDatabaseUiModelMapper) :
    CommonResultConverter<SendDataUseCase.Response, DatabaseUiModel>() {
    override fun convertSuccess(data: SendDataUseCase.Response) = mapper.map(data.transferState)
}