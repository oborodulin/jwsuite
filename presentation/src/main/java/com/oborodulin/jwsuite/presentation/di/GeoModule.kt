package com.oborodulin.jwsuite.presentation.di

import com.oborodulin.jwsuite.domain.usecases.geolocality.DeleteLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetAllLocalitiesUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalitiesUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.GetLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.geolocality.LocalityUseCases
import com.oborodulin.jwsuite.domain.usecases.geolocality.SaveLocalityUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.DeleteRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.GetRegionsUseCase
import com.oborodulin.jwsuite.domain.usecases.georegion.RegionUseCases
import com.oborodulin.jwsuite.domain.usecases.georegion.SaveRegionUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.DeleteRegionDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetRegionDistrictUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.GetRegionDistrictsUseCase
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.RegionDistrictUseCases
import com.oborodulin.jwsuite.domain.usecases.georegiondistrict.SaveRegionDistrictUseCase
import com.oborodulin.jwsuite.presentation.ui.model.converters.AllLocalitiesListConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.LocalitiesListConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.LocalityConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.RegionConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.RegionDistrictConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.RegionDistrictsListConverter
import com.oborodulin.jwsuite.presentation.ui.model.converters.RegionsListConverter
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalitiesListToLocalityListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalityToLocalitiesListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.region.RegionToRegionUiMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.region.RegionToRegionsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.region.RegionUiToRegionMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.region.RegionsListToRegionsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.regiondistrict.RegionDistrictToRegionDistrictUiMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.regiondistrict.RegionDistrictToRegionDistrictsListItemMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.regiondistrict.RegionDistrictUiToRegionDistrictMapper
import com.oborodulin.jwsuite.presentation.ui.model.mappers.regiondistrict.RegionDistrictsListToRegionDistrictsListItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoModule {
    // MAPPERS:
    // Regions:
    @Singleton
    @Provides
    fun provideRegionToRegionUiMapper(): RegionToRegionUiMapper = RegionToRegionUiMapper()

    @Singleton
    @Provides
    fun provideRegionUiToRegionMapper(): RegionUiToRegionMapper = RegionUiToRegionMapper()

    @Singleton
    @Provides
    fun provideRegionToRegionsListItemMapper(): RegionToRegionsListItemMapper =
        RegionToRegionsListItemMapper()

    @Singleton
    @Provides
    fun provideRegionsListToRegionsListItemMapper(mapper: RegionToRegionsListItemMapper): RegionsListToRegionsListItemMapper =
        RegionsListToRegionsListItemMapper(mapper = mapper)

    // RegionDistricts:
    @Singleton
    @Provides
    fun provideRegionDistrictToRegionDistrictUiMapper(mapper: RegionToRegionUiMapper): RegionDistrictToRegionDistrictUiMapper =
        RegionDistrictToRegionDistrictUiMapper(regionMapper = mapper)

    @Singleton
    @Provides
    fun provideRegionDistrictUiToRegionDistrictMapper(mapper: RegionUiToRegionMapper): RegionDistrictUiToRegionDistrictMapper =
        RegionDistrictUiToRegionDistrictMapper(regionUiMapper = mapper)

    @Singleton
    @Provides
    fun provideRegionDistrictToRegionDistrictsListItemMapper(): RegionDistrictToRegionDistrictsListItemMapper =
        RegionDistrictToRegionDistrictsListItemMapper()

    @Singleton
    @Provides
    fun provideRegionDistrictsListToRegionDistrictsListItemMapper(mapper: RegionDistrictToRegionDistrictsListItemMapper): RegionDistrictsListToRegionDistrictsListItemMapper =
        RegionDistrictsListToRegionDistrictsListItemMapper(mapper = mapper)

    // Localities:
    @Singleton
    @Provides
    fun provideLocalityToLocalityUiMapper(
        regionMapper: RegionToRegionUiMapper,
        regionDistrictMapper: RegionDistrictToRegionDistrictUiMapper
    ): LocalityToLocalityUiMapper = LocalityToLocalityUiMapper(
        regionMapper = regionMapper, regionDistrictMapper = regionDistrictMapper
    )

    @Singleton
    @Provides
    fun provideLocalityUiToLocalityMapper(
        regionUiMapper: RegionUiToRegionMapper,
        regionUiDistrictMapper: RegionDistrictUiToRegionDistrictMapper
    ): LocalityUiToLocalityMapper = LocalityUiToLocalityMapper(
        regionUiMapper = regionUiMapper, regionUiDistrictMapper = regionUiDistrictMapper
    )

    @Singleton
    @Provides
    fun provideLocalityToLocalitiesListItemMapper(): LocalityToLocalitiesListItemMapper =
        LocalityToLocalitiesListItemMapper()

    @Singleton
    @Provides
    fun provideLocalitiesListToLocalityListItemMapper(mapper: LocalityToLocalitiesListItemMapper): LocalitiesListToLocalityListItemMapper =
        LocalitiesListToLocalityListItemMapper(mapper = mapper)

    // CONVERTERS:
    // Regions:
    @Singleton
    @Provides
    fun provideRegionsListConverter(mapper: RegionsListToRegionsListItemMapper): RegionsListConverter =
        RegionsListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideRegionConverter(mapper: RegionToRegionUiMapper): RegionConverter =
        RegionConverter(mapper = mapper)

    // RegionDistricts:
    @Singleton
    @Provides
    fun provideRegionDistrictsListConverter(mapper: RegionDistrictsListToRegionDistrictsListItemMapper): RegionDistrictsListConverter =
        RegionDistrictsListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideRegionDistrictConverter(mapper: RegionDistrictToRegionDistrictUiMapper): RegionDistrictConverter =
        RegionDistrictConverter(mapper = mapper)

    // Localities:
    @Singleton
    @Provides
    fun provideAllLocalitiesListConverter(mapper: LocalitiesListToLocalityListItemMapper): AllLocalitiesListConverter =
        AllLocalitiesListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideLocalitiesListConverter(mapper: LocalitiesListToLocalityListItemMapper): LocalitiesListConverter =
        LocalitiesListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideLocalityConverter(mapper: LocalityToLocalityUiMapper): LocalityConverter =
        LocalityConverter(mapper = mapper)

    // USE CASES:
    @Singleton
    @Provides
    fun provideRegionUseCases(
        getRegionsUseCase: GetRegionsUseCase,
        getRegionUseCase: GetRegionUseCase,
        saveRegionUseCase: SaveRegionUseCase,
        deleteRegionUseCase: DeleteRegionUseCase
    ): RegionUseCases = RegionUseCases(
        getRegionsUseCase,
        getRegionUseCase,
        saveRegionUseCase,
        deleteRegionUseCase
    )

    @Singleton
    @Provides
    fun provideRegionDistrictUseCases(
        getRegionDistrictsUseCase: GetRegionDistrictsUseCase,
        getRegionDistrictUseCase: GetRegionDistrictUseCase,
        saveRegionDistrictUseCase: SaveRegionDistrictUseCase,
        deleteRegionDistrictUseCase: DeleteRegionDistrictUseCase
    ): RegionDistrictUseCases = RegionDistrictUseCases(
        getRegionDistrictsUseCase,
        getRegionDistrictUseCase,
        saveRegionDistrictUseCase,
        deleteRegionDistrictUseCase
    )

    @Singleton
    @Provides
    fun provideLocalityUseCases(
        getLocalitiesUseCase: GetLocalitiesUseCase,
        getAllLocalitiesUseCase: GetAllLocalitiesUseCase,
        getLocalityUseCase: GetLocalityUseCase,
        saveLocalityUseCase: SaveLocalityUseCase,
        deleteLocalityUseCase: DeleteLocalityUseCase
    ): LocalityUseCases = LocalityUseCases(
        getLocalitiesUseCase,
        getAllLocalitiesUseCase,
        getLocalityUseCase,
        saveLocalityUseCase,
        deleteLocalityUseCase
    )
}