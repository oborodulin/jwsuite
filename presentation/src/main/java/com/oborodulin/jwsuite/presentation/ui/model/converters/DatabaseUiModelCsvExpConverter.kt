package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.db.CsvExportUseCase
import com.oborodulin.jwsuite.presentation.ui.model.DatabaseUiModel

class DatabaseUiModelCsvExpConverter :
    CommonResultConverter<CsvExportUseCase.Response, DatabaseUiModel>() {
    override fun convertSuccess(data: CsvExportUseCase.Response) = DatabaseUiModel(
        entityDesc = data.entityDesc,
        isDone = data.isDone
    )
}