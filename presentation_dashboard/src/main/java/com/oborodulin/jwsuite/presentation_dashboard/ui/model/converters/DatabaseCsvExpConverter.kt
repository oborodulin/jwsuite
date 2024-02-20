package com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters

import com.oborodulin.home.common.ui.state.CommonResultConverter
import com.oborodulin.jwsuite.domain.usecases.db.CsvExportUseCase
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DatabaseUiModel
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.mappers.ObjectsTransferStateToDatabaseUiModelMapper

class DatabaseCsvExpConverter(private val mapper: ObjectsTransferStateToDatabaseUiModelMapper) :
    CommonResultConverter<CsvExportUseCase.Response, DatabaseUiModel>() {
    override fun convertSuccess(data: CsvExportUseCase.Response) = mapper.map(data.transferState)
}