package com.oborodulin.jwsuite.presentation.di

import com.oborodulin.jwsuite.domain.usecases.territory.DeleteTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.SaveTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.domain.usecases.territorycategory.DeleteTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.SaveTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.TerritoryCategoryUseCases
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.mappers.CongregationUiToCongregationMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoriesListConverter
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoryCategoriesListConverter
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoryCategoryConverter
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.converters.TerritoryConverter
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoriesListToTerritoriesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryCategoriesListToTerritoryCategoriesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryCategoryToTerritoryCategoriesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryCategoryToTerritoryCategoryUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryCategoryUiToTerritoryCategoryMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryToTerritoriesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryToTerritoryUiMapper
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.TerritoryUiToTerritoryMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TerritoringModule {
    // MAPPERS:
    // Territory Category:
    @Singleton
    @Provides
    fun provideTerritoryCategoryToTerritoryCategoryUiMapper(): TerritoryCategoryToTerritoryCategoryUiMapper =
        TerritoryCategoryToTerritoryCategoryUiMapper()

    @Singleton
    @Provides
    fun provideTerritoryCategoryUiToTerritoryCategoryMapper(): TerritoryCategoryUiToTerritoryCategoryMapper =
        TerritoryCategoryUiToTerritoryCategoryMapper()

    @Singleton
    @Provides
    fun provideTerritoryCategoryToTerritoryCategoriesListItemMapper(): TerritoryCategoryToTerritoryCategoriesListItemMapper =
        TerritoryCategoryToTerritoryCategoriesListItemMapper()

    @Singleton
    @Provides
    fun provideTerritoryCategoriesListToTerritoryCategoriesListItemMapper(mapper: TerritoryCategoryToTerritoryCategoriesListItemMapper): TerritoryCategoriesListToTerritoryCategoriesListItemMapper =
        TerritoryCategoriesListToTerritoryCategoriesListItemMapper(mapper = mapper)

    // Territory:
    @Singleton
    @Provides
    fun provideTerritoryToTerritoryUiMapper(
        congregationMapper: CongregationToCongregationUiMapper,
        territoryCategoryMapper: TerritoryCategoryToTerritoryCategoryUiMapper,
        localityMapper: LocalityToLocalityUiMapper
    ): TerritoryToTerritoryUiMapper = TerritoryToTerritoryUiMapper(
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        localityMapper = localityMapper
    )

    @Singleton
    @Provides
    fun provideTerritoryUiToTerritoryMapper(
        congregationUiMapper: CongregationUiToCongregationMapper,
        territoryCategoryUiMapper: TerritoryCategoryUiToTerritoryCategoryMapper,
        localityUiMapper: LocalityUiToLocalityMapper
    ): TerritoryUiToTerritoryMapper = TerritoryUiToTerritoryMapper(
        congregationUiMapper = congregationUiMapper,
        territoryCategoryUiMapper = territoryCategoryUiMapper,
        localityUiMapper = localityUiMapper
    )

    @Singleton
    @Provides
    fun provideTerritoryToTerritoriesListItemMapper(
        congregationMapper: CongregationToCongregationUiMapper,
        territoryCategoryMapper: TerritoryCategoryToTerritoryCategoryUiMapper,
        localityMapper: LocalityToLocalityUiMapper
    ): TerritoryToTerritoriesListItemMapper = TerritoryToTerritoriesListItemMapper(
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        localityMapper = localityMapper
    )

    @Singleton
    @Provides
    fun provideTerritoriesListToTerritoriesListItemMapper(mapper: TerritoryToTerritoriesListItemMapper): TerritoriesListToTerritoriesListItemMapper =
        TerritoriesListToTerritoriesListItemMapper(mapper = mapper)

    // CONVERTERS:
    // Territory Category:
    @Singleton
    @Provides
    fun provideTerritoryCategoryConverter(mapper: TerritoryCategoryToTerritoryCategoryUiMapper): TerritoryCategoryConverter =
        TerritoryCategoryConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryCategoriesListConverter(mapper: TerritoryCategoriesListToTerritoryCategoriesListItemMapper): TerritoryCategoriesListConverter =
        TerritoryCategoriesListConverter(mapper = mapper)

    // Territory:
    @Singleton
    @Provides
    fun provideTerritoryConverter(mapper: TerritoryToTerritoryUiMapper): TerritoryConverter =
        TerritoryConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoriesListConverter(mapper: TerritoriesListToTerritoriesListItemMapper): TerritoriesListConverter =
        TerritoriesListConverter(mapper = mapper)

    // USE CASES:
    // Territory Category:
    @Singleton
    @Provides
    fun provideTerritoryCategoryUseCases(
        getTerritoryCategoriesUseCase: GetTerritoryCategoriesUseCase,
        getTerritoryCategoryUseCase: GetTerritoryCategoryUseCase,
        saveTerritoryCategoryUseCase: SaveTerritoryCategoryUseCase,
        deleteTerritoryCategoryUseCase: DeleteTerritoryCategoryUseCase
    ): TerritoryCategoryUseCases = TerritoryCategoryUseCases(
        getTerritoryCategoriesUseCase,
        getTerritoryCategoryUseCase,
        saveTerritoryCategoryUseCase,
        deleteTerritoryCategoryUseCase
    )

    // Territory:
    @Singleton
    @Provides
    fun provideTerritoryUseCases(
        getTerritoriesUseCase: GetTerritoriesUseCase,
        getTerritoryUseCase: GetTerritoryUseCase,
        saveTerritoryUseCase: SaveTerritoryUseCase,
        deleteTerritoryUseCase: DeleteTerritoryUseCase
    ): TerritoryUseCases = TerritoryUseCases(
        getTerritoriesUseCase,
        getTerritoryUseCase,
        saveTerritoryUseCase,
        deleteTerritoryUseCase
    )
}