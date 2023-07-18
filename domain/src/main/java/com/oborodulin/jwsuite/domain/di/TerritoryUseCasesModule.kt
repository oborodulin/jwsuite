package com.oborodulin.jwsuite.domain.di

import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.territory.DeleteTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.SaveTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.DeleteTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.SaveTerritoryCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TerritoryUseCasesModule {
    // Territory:
    @Singleton
    @Provides
    fun provideGetTerritoryUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): GetTerritoryUseCase = GetTerritoryUseCase(configuration, territoriesRepository)

    @Singleton
    @Provides
    fun provideGetTerritoriesUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): GetTerritoriesUseCase = GetTerritoriesUseCase(configuration, territoriesRepository)

    @Singleton
    @Provides
    fun provideDeleteTerritoryUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): DeleteTerritoryUseCase = DeleteTerritoryUseCase(configuration, territoriesRepository)

    @Singleton
    @Provides
    fun provideSaveTerritoryUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): SaveTerritoryUseCase = SaveTerritoryUseCase(configuration, territoriesRepository)

    // Territory Category:
    @Singleton
    @Provides
    fun provideGetTerritoryCategoryUseCase(
        configuration: UseCase.Configuration,
        territoryCategoriesRepository: TerritoryCategoriesRepository
    ): GetTerritoryCategoryUseCase =
        GetTerritoryCategoryUseCase(configuration, territoryCategoriesRepository)

    @Singleton
    @Provides
    fun provideGetTerritoryCategoriesUseCase(
        configuration: UseCase.Configuration,
        territoryCategoriesRepository: TerritoryCategoriesRepository
    ): GetTerritoryCategoriesUseCase =
        GetTerritoryCategoriesUseCase(configuration, territoryCategoriesRepository)

    @Singleton
    @Provides
    fun provideDeleteTerritoryCategoryUseCase(
        configuration: UseCase.Configuration,
        territoryCategoriesRepository: TerritoryCategoriesRepository
    ): DeleteTerritoryCategoryUseCase =
        DeleteTerritoryCategoryUseCase(configuration, territoryCategoriesRepository)

    @Singleton
    @Provides
    fun provideSaveTerritoryCategoryUseCase(
        configuration: UseCase.Configuration,
        territoryCategoriesRepository: TerritoryCategoriesRepository
    ): SaveTerritoryCategoryUseCase =
        SaveTerritoryCategoryUseCase(configuration, territoryCategoriesRepository)

}