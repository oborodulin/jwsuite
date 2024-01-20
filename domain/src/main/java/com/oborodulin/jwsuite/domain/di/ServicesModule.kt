package com.oborodulin.jwsuite.domain.di

import com.oborodulin.jwsuite.domain.services.ExportService
import com.oborodulin.jwsuite.domain.services.ImportService
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {
    // Export:
    @Singleton
    @Provides
    fun provideExportService(): ExportService = ExportService

    // Import:
    @Singleton
    @Provides
    fun provideImportService(): ImportService = ImportService
}