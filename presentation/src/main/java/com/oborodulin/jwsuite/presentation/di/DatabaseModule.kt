package com.oborodulin.jwsuite.presentation.di

import com.oborodulin.jwsuite.domain.usecases.db.CsvExportUseCase
import com.oborodulin.jwsuite.domain.usecases.db.CsvImportUseCase
import com.oborodulin.jwsuite.domain.usecases.db.DataReceptionUseCase
import com.oborodulin.jwsuite.domain.usecases.db.DataTransmissionUseCase
import com.oborodulin.jwsuite.domain.usecases.db.DatabaseUseCases
import com.oborodulin.jwsuite.presentation.ui.model.converters.DatabaseUiModelCsvExpConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.DatabaseUiModelCsvImpConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    // MAPPERS:

    // CONVERTERS:
    @Singleton
    @Provides
    fun provideDatabaseUiModelCsvExpConverter(): DatabaseUiModelCsvExpConverter =
        DatabaseUiModelCsvExpConverter()

    @Singleton
    @Provides
    fun provideDatabaseUiModelCsvImpConverter(): DatabaseUiModelCsvImpConverter =
        DatabaseUiModelCsvImpConverter()

    // USE CASES:
    @Singleton
    @Provides
    fun provideDatabaseUseCases(
        csvExportUseCase: CsvExportUseCase,
        csvImportUseCase: CsvImportUseCase,
        dataTransmissionUseCase: DataTransmissionUseCase,
        dataReceptionUseCase: DataReceptionUseCase
    ): DatabaseUseCases = DatabaseUseCases(
        csvExportUseCase,
        csvImportUseCase,
        dataTransmissionUseCase,
        dataReceptionUseCase
    )
}