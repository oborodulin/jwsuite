package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.WorkerProviderRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.worker.CreateLogoutWorkUseCase
import com.oborodulin.jwsuite.domain.usecases.worker.LogoutWorkerOnSuccessUseCase
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
    ): CreateLogoutWorkUseCase = CreateLogoutWorkUseCase(configuration, workerRepository)

    @Singleton
    @Provides
    fun provideWorkerOnSuccessUseCase(
        configuration: UseCase.Configuration, workerRepository: WorkerProviderRepository
    ): LogoutWorkerOnSuccessUseCase = LogoutWorkerOnSuccessUseCase(configuration, workerRepository)
}