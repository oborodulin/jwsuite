package com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.db.CsvImportUseCase
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DatabaseUiModel
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.mappers.ObjectsTransferStateToDatabaseUiModelMapper

class DatabaseCsvImpConverter(private val mapper: ObjectsTransferStateToDatabaseUiModelMapper) :
    CommonResultConverter<CsvImportUseCase.Response, DatabaseUiModel>() {
    override fun convertSuccess(data: CsvImportUseCase.Response) = mapper.map(data.transferState)
}