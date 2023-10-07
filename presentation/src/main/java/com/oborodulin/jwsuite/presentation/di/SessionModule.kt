package com.oborodulin.jwsuite.presentation.di

import com.oborodulin.jwsuite.domain.usecases.session.SessionUseCases
import com.oborodulin.jwsuite.domain.usecases.session.SignoutUseCase
import com.oborodulin.jwsuite.domain.usecases.session.SignupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {
    // MAPPERS:

    // CONVERTERS:

    // USE CASES:
    @Singleton
    @Provides
    fun provideSessionUseCases(
        signupUseCase: SignupUseCase,
        signoutUseCase: SignoutUseCase
    ): SessionUseCases = SessionUseCases(
        signupUseCase,
        signoutUseCase
    )
}