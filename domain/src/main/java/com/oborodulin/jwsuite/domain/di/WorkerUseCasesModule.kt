package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.WorkerProviderRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.worker.CreateWorkUseCase
import com.oborodulin.jwsuite.domain.usecases.worker.WorkerOnSuccessUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerUseCasesModule {
    @Singleton
    @Provides
    fun provideCreateWorkUseCase(
        configuration: UseCase.Configuration, workerRepository: WorkerProviderRepository
    ): CreateWorkUseCase = CreateWorkUseCase(configuration, workerRepository)

    @Singleton
    @Provides
    fun provideWorkerOnSuccessUseCase(
        configuration: UseCase.Configuration, workerRepository: WorkerProviderRepository
    ): WorkerOnSuccessUseCase = WorkerOnSuccessUseCase(configuration, workerRepository)
}