package com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.db.ReceiveDataUseCase
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DatabaseUiModel

class DatabaseReceiveConverter :
    CommonResultConverter<ReceiveDataUseCase.Response, DatabaseUiModel>() {
    override fun convertSuccess(data: ReceiveDataUseCase.Response) = DatabaseUiModel(
        entityDesc = data.entityDesc,
        progress = data.methodIndex.toFloat() / (data.totalMethods / 100),
        isSuccess = data.isSuccess,
        isDone = data.isDone
    )
}