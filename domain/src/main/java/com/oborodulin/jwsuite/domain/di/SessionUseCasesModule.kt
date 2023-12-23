package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.session.CheckPasswordValidUseCase
import com.oborodulin.jwsuite.domain.usecases.session.GetSessionUseCase
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
    fun provideGetSessionUseCase(
        configuration: UseCase.Configuration, sessionManagerRepository: SessionManagerRepository
    ): GetSessionUseCase = GetSessionUseCase(configuration, sessionManagerRepository)

    @Singleton
    @Provides
    fun provideSignupUseCase(
        configuration: UseCase.Configuration, sessionManagerRepository: SessionManagerRepository
        //, membersRepository: MembersRepository
    ): SignupUseCase = SignupUseCase(configuration, sessionManagerRepository)//, membersRepository)

    @Singleton
    @Provides
    fun provideSignoutUseCase(
        configuration: UseCase.Configuration, sessionManagerRepository: SessionManagerRepository
    ): SignoutUseCase = SignoutUseCase(configuration, sessionManagerRepository)

    @Singleton
    @Provides
    fun provideCheckPasswordValidUseCase(
        configuration: UseCase.Configuration, sessionManagerRepository: SessionManagerRepository,
        membersRepository: MembersRepository
    ): CheckPasswordValidUseCase =
        CheckPasswordValidUseCase(configuration, sessionManagerRepository, membersRepository)

    @Singleton
    @Provides
    fun provideLoginUseCase(
        configuration: UseCase.Configuration, sessionManagerRepository: SessionManagerRepository,
        membersRepository: MembersRepository
    ): LoginUseCase = LoginUseCase(configuration, sessionManagerRepository, membersRepository)

    @Singleton
    @Provides
    fun provideLogoutUseCase(
        configuration: UseCase.Configuration, sessionManagerRepository: SessionManagerRepository
    ): LogoutUseCase = LogoutUseCase(configuration, sessionManagerRepository)
}