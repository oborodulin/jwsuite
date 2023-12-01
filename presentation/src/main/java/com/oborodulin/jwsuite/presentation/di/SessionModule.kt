package com.oborodulin.jwsuite.presentation.di

import com.oborodulin.jwsuite.domain.usecases.session.GetSessionUseCase
import com.oborodulin.jwsuite.domain.usecases.session.LoginUseCase
import com.oborodulin.jwsuite.domain.usecases.session.LogoutUseCase
import com.oborodulin.jwsuite.domain.usecases.session.SessionUseCases
import com.oborodulin.jwsuite.domain.usecases.session.SignoutUseCase
import com.oborodulin.jwsuite.domain.usecases.session.SignupUseCase
import com.oborodulin.jwsuite.presentation.ui.model.converters.LoginSessionConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.LogoutSessionConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.SessionConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.SignoutSessionConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.SignupSessionConverter
import com.oborodulin.jwsuite.presentation.ui.model.mappers.RoleToRolesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.RolesListToRolesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.SessionToSessionUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {
    // MAPPERS:
    @Singleton
    @Provides
    fun provideRoleToRolesListItemMapper(): RoleToRolesListItemMapper = RoleToRolesListItemMapper()

    @Singleton
    @Provides
    fun provideRolesListToRolesListItemMapper(mapper: RoleToRolesListItemMapper): RolesListToRolesListItemMapper =
        RolesListToRolesListItemMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideSessionToSessionUiMapper(mapper: RolesListToRolesListItemMapper): SessionToSessionUiMapper =
        SessionToSessionUiMapper(mapper = mapper)

    // CONVERTERS:
    @Singleton
    @Provides
    fun provideSessionConverter(mapper: SessionToSessionUiMapper): SessionConverter =
        SessionConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideSignupSessionConverter(mapper: SessionToSessionUiMapper): SignupSessionConverter =
        SignupSessionConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideSignoutSessionConverter(mapper: SessionToSessionUiMapper): SignoutSessionConverter =
        SignoutSessionConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideLoginSessionConverter(mapper: SessionToSessionUiMapper): LoginSessionConverter =
        LoginSessionConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideLogoutSessionConverter(mapper: SessionToSessionUiMapper): LogoutSessionConverter =
        LogoutSessionConverter(mapper = mapper)

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