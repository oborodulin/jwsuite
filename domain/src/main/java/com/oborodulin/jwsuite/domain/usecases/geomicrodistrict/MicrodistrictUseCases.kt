package com.oborodulin.jwsuite.domain.usecases.geomicrodistrict

data class MicrodistrictUseCases(
    val getMicrodistrictsUseCase: GetMicrodistrictsUseCase,
    val getMicrodistrictUseCase: GetMicrodistrictUseCase,
    val saveMicrodistrictUseCase: SaveMicrodistrictUseCase,
    val deleteMicrodistrictUseCase: DeleteMicrodistrictUseCase
)