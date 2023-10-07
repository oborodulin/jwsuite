package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.session.SignoutUseCase
import com.oborodulin.jwsuite.domain.usecases.session.SignupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionUseCasesModule {
    @Singleton
    @Provides
    fun provideSignupUseCase(
        configuration: UseCase.Configuration, sessionManagerRepository: SessionManagerRepository
    ): SignupUseCase = SignupUseCase(configuration, sessionManagerRepository)

    @Singleton
    @Provides
    fun provideSignoutUseCase(
        configuration: UseCase.Configuration, sessionManagerRepository: SessionManagerRepository
    ): SignoutUseCase = SignoutUseCase(configuration, sessionManagerRepository)
}