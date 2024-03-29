package com.oborodulin.jwsuite.presentation_territory.di

import android.content.Context
import com.oborodulin.jwsuite.domain.usecases.TerritoringUseCases
import com.oborodulin.jwsuite.domain.usecases.territory.DeleteTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetCongregationTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetFavoriteTerritoryTotalsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetNextTerritoryNumUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetProcessAndLocationTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryDetailsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryLocationsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.GetTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.HandOutTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.ProcessTerritoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.SaveTerritoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.TerritoryUseCases
import com.oborodulin.jwsuite.domain.usecases.territory.street.DeleteTerritoryStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetTerritoryStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.GetTerritoryStreetsUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.SaveTerritoryStreetUseCase
import com.oborodulin.jwsuite.domain.usecases.territory.street.TerritoryStreetUseCases
import com.oborodulin.jwsuite.domain.usecases.territorycategory.DeleteTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoriesUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.GetTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.SaveTerritoryCategoryUseCase
import com.oborodulin.jwsuite.domain.usecases.territorycategory.TerritoryCategoryUseCases
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationToCongregationUiMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.congregation.CongregationUiToCongregationMapper
import com.oborodulin.jwsuite.presentation_congregation.ui.model.mappers.member.MemberToMemberUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityToLocalityUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.locality.LocalityUiToLocalityMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictToLocalityDistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.localitydistrict.LocalityDistrictUiToLocalityDistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictToMicrodistrictUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.microdistrict.MicrodistrictUiToMicrodistrictMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetToStreetUiMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetUiToStreetMapper
import com.oborodulin.jwsuite.presentation_geo.ui.model.mappers.street.StreetsListToStreetsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoriesGridConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoriesListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryCategoriesListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryCategoryConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryDetailsListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryEntrancesListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryHousesListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryLocationsListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryRoomsListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryStreetConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.converters.TerritoryStreetsListConverter
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoriesListToTerritoriesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryDetailToTerritoryDetailsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryDetailsListToTerritoryDetailsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryLocationToTerritoryLocationsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryLocationsListToTerritoryLocationsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoriesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryToTerritoryUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.TerritoryUiToTerritoryMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category.TerritoryCategoriesListToTerritoryCategoriesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category.TerritoryCategoryToTerritoryCategoriesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category.TerritoryCategoryToTerritoryCategoryUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.category.TerritoryCategoryUiToTerritoryCategoryMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance.EntrancesListToEntrancesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house.HousesListToHousesListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.room.RoomsListToRoomsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetToTerritoryStreetUiMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetToTerritoryStreetsListItemMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetUiToTerritoryStreetMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetWithTerritoryAndStreetsToTerritoryStreetUiModelMapper
import com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street.TerritoryStreetsListToTerritoryStreetsListItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    // Territory Location:
    @Singleton
    @Provides
    fun provideTerritoryLocationToTerritoryLocationsListItemMapper(): TerritoryLocationToTerritoryLocationsListItemMapper =
        TerritoryLocationToTerritoryLocationsListItemMapper()

    @Singleton
    @Provides
    fun provideTerritoryLocationsListToTerritoryLocationsListItemMapper(mapper: TerritoryLocationToTerritoryLocationsListItemMapper): TerritoryLocationsListToTerritoryLocationsListItemMapper =
        TerritoryLocationsListToTerritoryLocationsListItemMapper(mapper = mapper)

    // Territory Details:
    @Singleton
    @Provides
    fun provideTerritoryDetailToTerritoryDetailsListItemMapper(): TerritoryDetailToTerritoryDetailsListItemMapper =
        TerritoryDetailToTerritoryDetailsListItemMapper()

    @Singleton
    @Provides
    fun provideTerritoryDetailsListToTerritoryDetailsListItemMapper(mapper: TerritoryDetailToTerritoryDetailsListItemMapper): TerritoryDetailsListToTerritoryDetailsListItemMapper =
        TerritoryDetailsListToTerritoryDetailsListItemMapper(mapper = mapper)

    // Territory Streets:
    @Singleton
    @Provides
    fun provideTerritoryStreetToTerritoryStreetUiMapper(mapper: StreetToStreetUiMapper): TerritoryStreetToTerritoryStreetUiMapper =
        TerritoryStreetToTerritoryStreetUiMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryStreetUiToTerritoryStreetMapper(mapper: StreetUiToStreetMapper): TerritoryStreetUiToTerritoryStreetMapper =
        TerritoryStreetUiToTerritoryStreetMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryStreetToTerritoryStreetsListItemMapper(): TerritoryStreetToTerritoryStreetsListItemMapper =
        TerritoryStreetToTerritoryStreetsListItemMapper()

    @Singleton
    @Provides
    fun provideTerritoryStreetsListToTerritoryStreetsListItemMapper(mapper: TerritoryStreetToTerritoryStreetsListItemMapper): TerritoryStreetsListToTerritoryStreetsListItemMapper =
        TerritoryStreetsListToTerritoryStreetsListItemMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryStreetWithTerritoryAndStreetsToTerritoryStreetUiModelMapper(
        territoryStreetMapper: TerritoryStreetToTerritoryStreetUiMapper,
        territoryMapper: TerritoryToTerritoryUiMapper,
        streetListItemMapper: StreetsListToStreetsListItemMapper
    ): TerritoryStreetWithTerritoryAndStreetsToTerritoryStreetUiModelMapper =
        TerritoryStreetWithTerritoryAndStreetsToTerritoryStreetUiModelMapper(
            territoryStreetMapper = territoryStreetMapper,
            territoryMapper = territoryMapper,
            streetListItemMapper = streetListItemMapper
        )

    // Territory:
    @Singleton
    @Provides
    fun provideTerritoryToTerritoryUiMapper(
        congregationMapper: CongregationToCongregationUiMapper,
        territoryCategoryMapper: TerritoryCategoryToTerritoryCategoryUiMapper,
        localityMapper: LocalityToLocalityUiMapper,
        localityDistrictMapper: LocalityDistrictToLocalityDistrictUiMapper,
        microdistrictMapper: MicrodistrictToMicrodistrictUiMapper
    ): TerritoryToTerritoryUiMapper = TerritoryToTerritoryUiMapper(
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        localityMapper = localityMapper,
        localityDistrictMapper = localityDistrictMapper,
        microdistrictMapper = microdistrictMapper
    )

    @Singleton
    @Provides
    fun provideTerritoryUiToTerritoryMapper(
        @ApplicationContext ctx: Context,
        congregationUiMapper: CongregationUiToCongregationMapper,
        territoryCategoryUiMapper: TerritoryCategoryUiToTerritoryCategoryMapper,
        localityUiMapper: LocalityUiToLocalityMapper,
        localityDistrictUiMapper: LocalityDistrictUiToLocalityDistrictMapper,
        microdistrictUiMapper: MicrodistrictUiToMicrodistrictMapper
    ): TerritoryUiToTerritoryMapper = TerritoryUiToTerritoryMapper(
        ctx = ctx,
        congregationUiMapper = congregationUiMapper,
        territoryCategoryUiMapper = territoryCategoryUiMapper,
        localityUiMapper = localityUiMapper,
        localityDistrictUiMapper = localityDistrictUiMapper,
        microdistrictUiMapper = microdistrictUiMapper
    )

    @Singleton
    @Provides
    fun provideTerritoryToTerritoriesListItemMapper(
        congregationMapper: CongregationToCongregationUiMapper,
        territoryCategoryMapper: TerritoryCategoryToTerritoryCategoryUiMapper,
        localityMapper: LocalityToLocalityUiMapper,
        memberMapper: MemberToMemberUiMapper
    ): TerritoryToTerritoriesListItemMapper = TerritoryToTerritoriesListItemMapper(
        congregationMapper = congregationMapper,
        territoryCategoryMapper = territoryCategoryMapper,
        localityMapper = localityMapper,
        memberMapper = memberMapper
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
    fun provideTerritoriesGridConverter(mapper: TerritoriesListToTerritoriesListItemMapper): TerritoriesGridConverter =
        TerritoriesGridConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoriesListConverter(mapper: TerritoriesListToTerritoriesListItemMapper): TerritoriesListConverter =
        TerritoriesListConverter(mapper = mapper)

    // Territory Locations:
    @Singleton
    @Provides
    fun provideTerritoryLocationsListConverter(mapper: TerritoryLocationsListToTerritoryLocationsListItemMapper): TerritoryLocationsListConverter =
        TerritoryLocationsListConverter(mapper = mapper)

    // Territory Details:
    @Singleton
    @Provides
    fun provideTerritoryDetailsListConverter(mapper: TerritoryDetailsListToTerritoryDetailsListItemMapper): TerritoryDetailsListConverter =
        TerritoryDetailsListConverter(mapper = mapper)

    // Territory Streets:
    @Singleton
    @Provides
    fun provideTerritoryStreetConverter(mapper: TerritoryStreetWithTerritoryAndStreetsToTerritoryStreetUiModelMapper): TerritoryStreetConverter =
        TerritoryStreetConverter(mapper = mapper)

    @Singleton
    @Provides
    fun provideTerritoryStreetsListConverter(mapper: TerritoryStreetsListToTerritoryStreetsListItemMapper): TerritoryStreetsListConverter =
        TerritoryStreetsListConverter(mapper = mapper)

    // House:
    @Singleton
    @Provides
    fun provideTerritoryHousesListConverter(
        territoryMapper: TerritoryToTerritoryUiMapper,
        housesListMapper: HousesListToHousesListItemMapper
    ): TerritoryHousesListConverter = TerritoryHousesListConverter(
        territoryMapper = territoryMapper, housesListMapper = housesListMapper
    )

    // Entrance:
    @Singleton
    @Provides
    fun provideTerritoryEntrancesListConverter(
        territoryMapper: TerritoryToTerritoryUiMapper,
        entrancesListMapper: EntrancesListToEntrancesListItemMapper
    ): TerritoryEntrancesListConverter = TerritoryEntrancesListConverter(
        territoryMapper = territoryMapper, entrancesListMapper = entrancesListMapper
    )

    // Room:
    @Singleton
    @Provides
    fun provideTerritoryRoomsListConverter(
        territoryMapper: TerritoryToTerritoryUiMapper,
        roomsListMapper: RoomsListToRoomsListItemMapper
    ): TerritoryRoomsListConverter = TerritoryRoomsListConverter(
        territoryMapper = territoryMapper, roomsListMapper = roomsListMapper
    )

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

    // Territory Street:
    @Singleton
    @Provides
    fun provideTerritoryStreetUseCases(
        getTerritoryStreetsUseCase: GetTerritoryStreetsUseCase,
        getTerritoryStreetUseCase: GetTerritoryStreetUseCase,
        saveTerritoryStreetUseCase: SaveTerritoryStreetUseCase,
        deleteTerritoryStreetUseCase: DeleteTerritoryStreetUseCase
    ): TerritoryStreetUseCases = TerritoryStreetUseCases(
        getTerritoryStreetsUseCase,
        getTerritoryStreetUseCase,
        saveTerritoryStreetUseCase,
        deleteTerritoryStreetUseCase,
    )

    // Territory:
    @Singleton
    @Provides
    fun provideTerritoryUseCases(
        getProcessAndLocationTerritoriesUseCase: GetProcessAndLocationTerritoriesUseCase,
        getCongregationTerritoriesUseCase: GetCongregationTerritoriesUseCase,
        getTerritoryUseCase: GetTerritoryUseCase,
        getNextTerritoryNumUseCase: GetNextTerritoryNumUseCase,
        saveTerritoryUseCase: SaveTerritoryUseCase,
        deleteTerritoryUseCase: DeleteTerritoryUseCase,
        getTerritoryDetailsUseCase: GetTerritoryDetailsUseCase,
        getFavoriteTerritoryTotalsUseCase: GetFavoriteTerritoryTotalsUseCase,
        handOutTerritoriesUseCase: HandOutTerritoriesUseCase,
        processTerritoriesUseCase: ProcessTerritoriesUseCase
    ): TerritoryUseCases = TerritoryUseCases(
        getProcessAndLocationTerritoriesUseCase,
        getCongregationTerritoriesUseCase,
        getTerritoryUseCase,
        getNextTerritoryNumUseCase,
        saveTerritoryUseCase,
        deleteTerritoryUseCase,
        getTerritoryDetailsUseCase,
        getFavoriteTerritoryTotalsUseCase,
        handOutTerritoriesUseCase,
        processTerritoriesUseCase
    )

    // Territoring:
    @Singleton
    @Provides
    fun provideTerritoringUseCases(
        getTerritoryLocationsUseCase: GetTerritoryLocationsUseCase
    ): TerritoringUseCases = TerritoringUseCases(getTerritoryLocationsUseCase)
}