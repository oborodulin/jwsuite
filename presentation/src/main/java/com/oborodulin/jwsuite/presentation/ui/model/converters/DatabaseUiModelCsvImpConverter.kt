package com.oborodulin.jwsuite.presentation.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.db.CsvImportUseCase
import com.oborodulin.jwsuite.presentation.ui.model.DatabaseUiModel

class DatabaseUiModelCsvImpConverter :
    CommonResultConverter<CsvImportUseCase.Response, DatabaseUiModel>() {
    override fun convertSuccess(data: CsvImportUseCase.Response) = DatabaseUiModel(
        entityDesc = data.entityDesc,
        isDone = data.isDone
    )
}