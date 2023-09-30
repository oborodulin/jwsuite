package com.oborodulin.jwsuite.domain.usecases.congregation

data class CongregationUseCases(
    val getCongregationsUseCase: GetCongregationsUseCase,
    val getCongregationUseCase: GetCongregationUseCase,
    val getFavoriteCongregationUseCase: GetFavoriteCongregationUseCase,
    val getFavoriteCongregationTotalsUseCase: GetFavoriteCongregationTotalsUseCase,
    val saveCongregationUseCase: SaveCongregationUseCase,
    val deleteCongregationUseCase: DeleteCongregationUseCase,
    val makeFavoriteCongregationUseCase: MakeFavoriteCongregationUseCase
)