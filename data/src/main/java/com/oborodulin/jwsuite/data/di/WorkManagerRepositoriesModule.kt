package com.oborodulin.jwsuite.data.di

import androidx.work.WorkManager
import com.oborodulin.jwsuite.data.local.worker.repositories.WorkerProviderRepositoryImpl
import com.oborodulin.jwsuite.domain.repositories.WorkerProviderRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerRepositoriesModule {
    @Provides
    @Singleton
    fun provideWorkerProviderRepository(workManager: WorkManager): WorkerProviderRepository =
        WorkerProviderRepositoryImpl(workManager)
}