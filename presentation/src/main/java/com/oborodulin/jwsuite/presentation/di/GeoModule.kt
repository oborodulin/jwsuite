package com.oborodulin.jwsuite.presentation.di

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
    fun provideRegionDistrictToRegionDistrictUiMapper(): RegionDistrictToRegionDistrictUiMapper =
        RegionDistrictToRegionDistrictUiMapper()

    @Singleton
    @Provides
    fun provideRegionDistrictUiToRegionDistrictMapper(): RegionDistrictUiToRegionDistrictMapper =
        RegionDistrictUiToRegionDistrictMapper()

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
    fun provideLocalityToLocalityUiMapper(): LocalityToLocalityUiMapper =
        LocalityToLocalityUiMapper()

    @Singleton
    @Provides
    fun provideLocalityUiToLocalityMapper(): LocalityUiToLocalityMapper =
        LocalityUiToLocalityMapper()

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
    fun provideLocalitiesListConverter(mapper: LocalitiesListToLocalityListItemMapper): LocalitiesListConverter =
        LocalitiesListConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideLocalityConverter(mapper: LocalityToLocalityUiMapper): LocalityConverter =
        LocalityConverter(mapper = mapper)
}