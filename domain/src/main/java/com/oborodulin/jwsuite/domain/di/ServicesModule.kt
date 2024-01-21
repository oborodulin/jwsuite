package com.oborodulin.jwsuite.domain.di

import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import com.oborodulin.jwsuite.domain.repositories.EntrancesRepository
import com.oborodulin.jwsuite.domain.repositories.FloorsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoLocalitiesRepository
import com.oborodulin.jwsuite.domain.repositories.GeoLocalityDistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoMicrodistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoRegionDistrictsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import com.oborodulin.jwsuite.domain.repositories.GroupsRepository
import com.oborodulin.jwsuite.domain.repositories.HousesRepository
import com.oborodulin.jwsuite.domain.repositories.MembersRepository
import com.oborodulin.jwsuite.domain.repositories.RoomsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryStreetsRepository
import com.oborodulin.jwsuite.domain.services.ExportService
import com.oborodulin.jwsuite.domain.services.ImportService
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServicesModule {
    // https://stackoverflow.com/questions/73786464/hilt-inject-a-list-of-interface-implementations
    @Provides
    fun providesCsvTransferableRepos(
        appSettingsRepository: AppSettingsRepository,
        // Congregations
        congregationsRepository: CongregationsRepository,
        groupsRepository: GroupsRepository,
        membersRepository: MembersRepository,
        // Geo
        geoRegionsRepository: GeoRegionsRepository,
        geoRegionDistrictsRepository: GeoRegionDistrictsRepository,
        geoLocalitiesRepository: GeoLocalitiesRepository,
        geoLocalityDistrictsRepository: GeoLocalityDistrictsRepository,
        geoMicrodistrictsRepository: GeoMicrodistrictsRepository,
        geoStreetsRepository: GeoStreetsRepository,
        // Territories
        territoryCategoriesRepository: TerritoryCategoriesRepository,
        territoriesRepository: TerritoriesRepository,
        territoryStreetsRepository: TerritoryStreetsRepository,
        // Housing
        housesRepository: HousesRepository,
        entrancesRepository: EntrancesRepository,
        floorsRepository: FloorsRepository,
        roomsRepository: RoomsRepository,
        // Territory Reports
        territoryReportsRepository: TerritoryReportsRepository
    ): List<CsvTransferableRepo> {
        return listOf(
            appSettingsRepository,
            // Congregations
            congregationsRepository,
            groupsRepository,
            membersRepository,
            // Geo
            geoRegionsRepository,
            geoRegionDistrictsRepository,
            geoLocalitiesRepository,
            geoLocalityDistrictsRepository,
            geoMicrodistrictsRepository,
            geoStreetsRepository,
            // Territories
            territoryCategoriesRepository,
            territoriesRepository,
            territoryStreetsRepository,
            // Housing
            housesRepository,
            entrancesRepository,
            floorsRepository,
            roomsRepository,
            // Territory Reports
            territoryReportsRepository
        )
    }

    // Export:
    @Singleton
    @Provides
    fun provideExportService(csvRepositories: List<CsvTransferableRepo>): ExportService =
        ExportService(csvRepositories)

    // Import:
    @Singleton
    @Provides
    fun provideImportService(csvRepositories: List<CsvTransferableRepo>): ImportService =
        ImportService(csvRepositories)
}