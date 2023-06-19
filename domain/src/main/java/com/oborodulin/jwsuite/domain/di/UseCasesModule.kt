package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import com.oborodulin.jwsuite.domain.usecases.*
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
    ): GetPayerUseCase = GetPayerUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideFavoritePayerUseCase(
        configuration: UseCase.Configuration,
        congregationsRepository: CongregationsRepository
    ): FavoritePayerUseCase = FavoritePayerUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideGetFavoritePayerUseCase(
        configuration: UseCase.Configuration,
        congregationsRepository: CongregationsRepository
    ): GetFavoritePayerUseCase = GetFavoritePayerUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideDeletePayerUseCase(
        configuration: UseCase.Configuration,
        congregationsRepository: CongregationsRepository
    ): DeletePayerUseCase = DeletePayerUseCase(configuration, congregationsRepository)

    @Singleton
    @Provides
    fun provideSavePayerUseCase(
        configuration: UseCase.Configuration,
        congregationsRepository: CongregationsRepository
    ): SavePayerUseCase = SavePayerUseCase(configuration, congregationsRepository)
}