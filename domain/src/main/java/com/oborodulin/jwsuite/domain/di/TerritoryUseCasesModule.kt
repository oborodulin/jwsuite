package com.oborodulin.jwsuite.domain.di

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.territory.DeleteTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryDetailsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryLocationsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.HandOutTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.SaveTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.DeleteTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.SaveTerritoryCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideGetTerritoryLocationsUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): GetTerritoryLocationsUseCase =
        GetTerritoryLocationsUseCase(configuration, territoriesRepository)

    @Singleton
    @Provides
    fun provideGetTerritoryDetailUseCase(
        configuration: UseCase.Configuration,
        @ApplicationContext ctx: Context, territoriesRepository: TerritoriesRepository
    ): GetTerritoryDetailsUseCase =
        GetTerritoryDetailsUseCase(configuration, ctx, territoriesRepository)

    @Singleton
    @Provides
    fun provideHandOutTerritoriesUseCase(
        configuration: UseCase.Configuration, territoriesRepository: TerritoriesRepository
    ): HandOutTerritoriesUseCase = HandOutTerritoriesUseCase(configuration, territoriesRepository)

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