package com.oborodulin.jwsuite.domain.di

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import com.oborodulin.jwsuite.domain.services.ExportService
import com.oborodulin.jwsuite.domain.services.ImportService
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.db.CsvExportUseCase
import com.oborodulin.jwsuite.domain.usecases.db.CsvImportUseCase
import com.oborodulin.jwsuite.domain.usecases.db.DataTransmissionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseUseCasesModule {
    @Singleton
    @Provides
    fun provideCsvExportUseCase(
        @ApplicationContext ctx: Context,
        configuration: UseCase.Configuration,
        exportService: ExportService
    ): CsvExportUseCase = CsvExportUseCase(ctx, configuration, exportService)

    @Singleton
    @Provides
    fun provideCsvImportUseCase(
        @ApplicationContext ctx: Context,
        configuration: UseCase.Configuration,
        importService: ImportService,
        databaseRepository: DatabaseRepository
    ): CsvImportUseCase = CsvImportUseCase(ctx, configuration, importService, databaseRepository)

    @Singleton
    @Provides
    fun provideDataTransmissionUseCase(
        @ApplicationContext ctx: Context,
        configuration: UseCase.Configuration,
        exportService: ExportService,
        sessionManagerRepository: SessionManagerRepository,
        membersRepository: MembersRepository,
        databaseRepository: DatabaseRepository
    ): DataTransmissionUseCase =
        DataTransmissionUseCase(
            ctx,
            configuration,
            exportService,
            sessionManagerRepository,
            membersRepository,
            databaseRepository
        )
}