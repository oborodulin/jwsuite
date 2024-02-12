package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.db.DataReceptionUseCase
import com.oborodulin.jwsuite.presentation.ui.model.DatabaseUiModel

class DatabaseUiModelReceiptConverter :
    CommonResultConverter<DataReceptionUseCase.Response, DatabaseUiModel>() {
    override fun convertSuccess(data: DataReceptionUseCase.Response) = DatabaseUiModel(
        entityDesc = data.entityDesc,
        isDone = data.isDone
    )
}