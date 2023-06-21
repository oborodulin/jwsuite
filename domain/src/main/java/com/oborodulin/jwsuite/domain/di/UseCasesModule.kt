package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.congregation.DeleteCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.MakeFavoriteCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.GetFavoriteCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.GetCongregationUseCase
import com.oborodulin.jwsuite.domain.usecases.congregation.SaveCongregationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    // USE CASES
    // Payer:
    @Singleton
    @Provides
    fun provideGetPayerUseCase(
        configuration: UseCase.Configuration,
        congregationsRepository: CongregationsRepository
    ): GetCongregationUseCase = GetCongregationUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideFavoritePayerUseCase(
        configuration: UseCase.Configuration,
        congregationsRepository: CongregationsRepository
    ): MakeFavoriteCongregationUseCase = MakeFavoriteCongregationUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideGetFavoritePayerUseCase(
        configuration: UseCase.Configuration,
        congregationsRepository: CongregationsRepository
    ): GetFavoriteCongregationUseCase = GetFavoriteCongregationUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideDeletePayerUseCase(
        configuration: UseCase.Configuration,
        congregationsRepository: CongregationsRepository
    ): DeleteCongregationUseCase = DeleteCongregationUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideSavePayerUseCase(
        configuration: UseCase.Configuration,
        congregationsRepository: CongregationsRepository
    ): SaveCongregationUseCase = SaveCongregationUseCase(configuration, congregationsRepository)
}