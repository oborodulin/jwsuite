package com.oborodulin.jwsuite.presentation.di

import com.oborodulin.jwsuite.domain.usecases.session.GetSessionUseCase
import com.oborodulin.jwsuite.domain.usecases.session.LoginUseCase
import com.oborodulin.jwsuite.domain.usecases.session.LogoutUseCase
import com.oborodulin.jwsuite.domain.usecases.session.SessionUseCases
import com.oborodulin.jwsuite.domain.usecases.session.SignoutUseCase
import com.oborodulin.jwsuite.domain.usecases.session.SignupUseCase
import com.oborodulin.jwsuite.presentation.ui.model.mappers.AppSettingToAppSettingUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppSettingModule {
    // MAPPERS:
    @Singleton
    @Provides
    fun provideAppSettingToAppSettingUiMapper(): AppSettingToAppSettingUiMapper =
        AppSettingToAppSettingUiMapper()

    // CONVERTERS:

    // USE CASES:
    @Singleton
    @Provides
    fun provideSessionUseCases(
        getSessionUseCase: GetSessionUseCase,
        signupUseCase: SignupUseCase,
        signoutUseCase: SignoutUseCase,
        loginUseCase: LoginUseCase,
        logoutUseCase: LogoutUseCase
    ): SessionUseCases = SessionUseCases(
        getSessionUseCase,
        signupUseCase,
        signoutUseCase,
        loginUseCase,
        logoutUseCase
    )
}