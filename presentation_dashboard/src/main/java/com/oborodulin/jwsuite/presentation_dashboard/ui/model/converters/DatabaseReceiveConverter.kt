package com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.db.ReceiveDataUseCase
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DatabaseUiModel
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.mappers.ObjectsTransferStateToDatabaseUiModelMapper

class DatabaseReceiveConverter(private val mapper: ObjectsTransferStateToDatabaseUiModelMapper) :
    CommonResultConverter<ReceiveDataUseCase.Response, DatabaseUiModel>() {
    override fun convertSuccess(data: ReceiveDataUseCase.Response) = mapper.map(data.transferState)
}