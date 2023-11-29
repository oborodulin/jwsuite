package com.oborodulin.jwsuite.presentation_dashboard.di

import com.oborodulin.jwsuite.domain.usecases.DashboardingUseCases
import com.oborodulin.jwsuite.domain.usecases.congregation.GetFavoriteCongregationUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.FavoriteCongregationConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardingModule {
    // MAPPERS:
    // Congregation:

    // CONVERTERS:
    // Congregation:
    @Singleton
    @Provides
    fun provideFavoriteCongregationConverter(mapper: CongregationToCongregationUiMapper): FavoriteCongregationConverter =
        FavoriteCongregationConverter(mapper = mapper)

    // USE CASES:
    @Singleton
    @Provides
    fun provideDashboardingUseCases(
        getFavoriteCongregationUseCase: GetFavoriteCongregationUseCase
    ): DashboardingUseCases = DashboardingUseCases(getFavoriteCongregationUseCase)
}