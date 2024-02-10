package com.oborodulin.jwsuite.domain.usecases.db

data class DatabaseUseCases(
    val csvExportUseCase: CsvExportUseCase,
    val csvImportUseCase: CsvImportUseCase,
    val dataTransmissionUseCase: DataTransmissionUseCase,
    val dataReceptionUseCase: DataReceptionUseCase
)