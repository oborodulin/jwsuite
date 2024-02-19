package com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.db.SendDataUseCase
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DatabaseUiModel

class DatabaseSendConverter :
    CommonResultConverter<SendDataUseCase.Response, DatabaseUiModel>() {
    override fun convertSuccess(data: SendDataUseCase.Response) = DatabaseUiModel(
        entityDesc = data.entityDesc,
        progress = data.methodNum.toFloat() / (data.totalMethods / 100),
        isSuccess = data.isSuccess,
        isDone = data.isDone
    )
}