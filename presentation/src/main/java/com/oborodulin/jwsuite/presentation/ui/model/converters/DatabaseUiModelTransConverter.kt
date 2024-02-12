package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.db.DataTransmissionUseCase
import com.oborodulin.jwsuite.presentation.ui.model.DatabaseUiModel

class DatabaseUiModelTransConverter :
    CommonResultConverter<DataTransmissionUseCase.Response, DatabaseUiModel>() {
    override fun convertSuccess(data: DataTransmissionUseCase.Response) = DatabaseUiModel(
        entityDesc = data.entityDesc,
        isDone = data.isDone
    )
}