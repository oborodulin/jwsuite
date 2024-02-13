package com.oborodulin.jwsuite.domain.usecases.db

data class DatabaseUseCases(
    val csvExportUseCase: CsvExportUseCase,
    val csvImportUseCase: CsvImportUseCase,
    val sendDataUseCase: SendDataUseCase,
    val receiveDataUseCase: ReceiveDataUseCase
)