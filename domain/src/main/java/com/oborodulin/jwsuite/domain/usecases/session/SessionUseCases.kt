package com.oborodulin.jwsuite.domain.usecases.session

data class SessionUseCases(
    val signupUseCase: SignupUseCase,
    val signoutUseCase: SignoutUseCase
)