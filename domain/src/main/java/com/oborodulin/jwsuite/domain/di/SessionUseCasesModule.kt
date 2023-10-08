package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.session.LoginUseCase
import com.oborodulin.jwsuite.domain.usecases.session.LogoutUseCase
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

    @Singleton
    @Provides
    fun provideLoginUseCase(
        configuration: UseCase.Configuration, sessionManagerRepository: SessionManagerRepository
    ): LoginUseCase = LoginUseCase(configuration, sessionManagerRepository)

    @Singleton
    @Provides
    fun provideLogoutUseCase(
        configuration: UseCase.Configuration, sessionManagerRepository: SessionManagerRepository
    ): LogoutUseCase = LogoutUseCase(configuration, sessionManagerRepository)
}