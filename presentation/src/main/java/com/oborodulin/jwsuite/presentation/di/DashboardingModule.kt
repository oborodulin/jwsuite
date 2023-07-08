package com.oborodulin.jwsuite.presentation.di

import com.oborodulin.jwsuite.domain.usecases.DashboardingUseCases
import com.oborodulin.jwsuite.domain.usecases.congregation.GetFavoriteCongregationUseCase
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.dashboarding.model.converters.FavoriteCongregationConverter
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