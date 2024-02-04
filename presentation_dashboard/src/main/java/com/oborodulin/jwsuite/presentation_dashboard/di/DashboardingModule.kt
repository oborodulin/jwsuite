package com.oborodulin.jwsuite.presentation_dashboard.di

import com.oborodulin.jwsuite.domain.usecases.DashboardingUseCases
import com.oborodulin.jwsuite.domain.usecases.dashboard.GetDashboardInfoUseCase
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.DashboardingConverter
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.converters.FavoriteCongregationConverter
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.mappers.CongregationTotalsToCongregationTotalsUiMapper
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.mappers.DashboardToDashboardingUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardingModule {
    // MAPPERS:
    @Singleton
    @Provides
    fun provideCongregationTotalsToCongregationTotalsUiMapper(mapper: CongregationToCongregationUiMapper): CongregationTotalsToCongregationTotalsUiMapper =
        CongregationTotalsToCongregationTotalsUiMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideDashboardToDashboardingUiMapper(
        congregationMapper: CongregationToCongregationUiMapper,
        totalsMapper: CongregationTotalsToCongregationTotalsUiMapper
    ): DashboardToDashboardingUiMapper = DashboardToDashboardingUiMapper(
        congregationMapper = congregationMapper,
        totalsMapper = totalsMapper
    )

    // CONVERTERS:
    // Congregation:
    @Singleton
    @Provides
    fun provideFavoriteCongregationConverter(mapper: CongregationToCongregationUiMapper): FavoriteCongregationConverter =
        FavoriteCongregationConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideDashboardingConverter(mapper: DashboardToDashboardingUiMapper): DashboardingConverter =
        DashboardingConverter(mapper = mapper)

    // USE CASES:
    @Singleton
    @Provides
    fun provideDashboardingUseCases(
        getDashboardInfoUseCase: GetDashboardInfoUseCase
    ): DashboardingUseCases = DashboardingUseCases(getDashboardInfoUseCase)
}