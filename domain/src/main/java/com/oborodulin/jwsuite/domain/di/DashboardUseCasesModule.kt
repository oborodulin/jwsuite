package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.dashboard.GetDashboardInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardUseCasesModule {
    @Singleton
    @Provides
    fun provideGetDashboardInfoUseCase(
        configuration: UseCase.Configuration,
        congregationsRepository: CongregationsRepository,
        territoriesRepository: TerritoriesRepository
    ): GetDashboardInfoUseCase =
        GetDashboardInfoUseCase(configuration, congregationsRepository, territoriesRepository)
}