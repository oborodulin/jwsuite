package com.oborodulin.jwsuite.domain.usecases.session

data class SessionUseCases(
    val getSessionUseCase: GetSessionUseCase,
    val signupUseCase: SignupUseCase,
    val signoutUseCase: SignoutUseCase,
    val checkPasswordValidUseCase: CheckPasswordValidUseCase,
    val loginUseCase: LoginUseCase,
    val logoutUseCase: LogoutUseCase
)