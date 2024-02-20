package com.oborodulin.jwsuite.presentation_dashboard.di

import com.oborodulin.jwsuite.domain.usecases.db.CsvExportUseCase
import com.oborodulin.jwsuite.domain.usecases.db.CsvImportUseCase
import com.oborodulin.jwsuite.domain.usecases.db.DatabaseUseCases
import com.oborodulin.jwsuite.domain.usecases.db.ReceiveDataUseCase
import com.oborodulin.jwsuite.domain.usecases.db.SendDataUseCase
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DatabaseCsvExpConverter
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DatabaseCsvImpConverter
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DatabaseReceiveConverter
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DatabaseSendConverter
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.mappers.ObjectsTransferStateToDatabaseUiModelMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    // MAPPERS:
    @Singleton
    @Provides
    fun provideObjectsTransferStateToDatabaseUiModelMapper(): ObjectsTransferStateToDatabaseUiModelMapper =
        ObjectsTransferStateToDatabaseUiModelMapper()

    // CONVERTERS:
    @Singleton
    @Provides
    fun provideDatabaseCsvExpConverter(mapper: ObjectsTransferStateToDatabaseUiModelMapper): DatabaseCsvExpConverter =
        DatabaseCsvExpConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideDatabaseCsvImpConverter(mapper: ObjectsTransferStateToDatabaseUiModelMapper): DatabaseCsvImpConverter =
        DatabaseCsvImpConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideDatabaseSendConverter(mapper: ObjectsTransferStateToDatabaseUiModelMapper): DatabaseSendConverter =
        DatabaseSendConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideDatabaseReceiveConverter(mapper: ObjectsTransferStateToDatabaseUiModelMapper): DatabaseReceiveConverter =
        DatabaseReceiveConverter(mapper = mapper)

    // USE CASES:
    @Singleton
    @Provides
    fun provideDatabaseUseCases(
        csvExportUseCase: CsvExportUseCase,
        csvImportUseCase: CsvImportUseCase,
        sendDataUseCase: SendDataUseCase,
        receiveDataUseCase: ReceiveDataUseCase
    ): DatabaseUseCases = DatabaseUseCases(
        csvExportUseCase,
        csvImportUseCase,
        sendDataUseCase,
        receiveDataUseCase
    )
}