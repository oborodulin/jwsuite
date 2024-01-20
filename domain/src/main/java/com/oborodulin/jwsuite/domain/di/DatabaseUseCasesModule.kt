package com.oborodulin.jwsuite.domain.di

import android.content.Context
import com.oborodulin.home.common.domain.usecases.UseCase
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import com.oborodulin.jwsuite.domain.repositories.DatabaseRepository
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
import com.oborodulin.jwsuite.domain.repositories.SessionManagerRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryCategoriesRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryReportsRepository
import com.oborodulin.jwsuite.domain.repositories.TerritoryStreetsRepository
import com.oborodulin.jwsuite.domain.services.ExportService
import com.oborodulin.jwsuite.domain.services.ImportService
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.usecases.*
import com.oborodulin.jwsuite.domain.usecases.db.CsvExportUseCase
import com.oborodulin.jwsuite.domain.usecases.db.CsvImportUseCase
import com.oborodulin.jwsuite.domain.usecases.db.DataTransmissionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseUseCasesModule {
    // https://stackoverflow.com/questions/73786464/hilt-inject-a-list-of-interface-implementations
    @Provides
    fun providesImplementations(
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

    @Singleton
    @Provides
    fun provideCsvExportUseCase(
        @ApplicationContext ctx: Context,
        configuration: UseCase.Configuration,
        exportService: ExportService,
        csvRepositories: List<CsvTransferableRepo>
    ): CsvExportUseCase = CsvExportUseCase(ctx, configuration, exportService, csvRepositories)

    @Singleton
    @Provides
    fun provideCsvImportUseCase(
        @ApplicationContext ctx: Context,
        configuration: UseCase.Configuration,
        importService: ImportService,
        databaseRepository: DatabaseRepository,
        csvRepositories: List<CsvTransferableRepo>
    ): CsvImportUseCase =
        CsvImportUseCase(ctx, configuration, importService, databaseRepository, csvRepositories)

    @Singleton
    @Provides
    fun provideDataTransmissionUseCase(
        @ApplicationContext ctx: Context,
        configuration: UseCase.Configuration,
        exportService: ExportService,
        sessionManagerRepository: SessionManagerRepository,
        membersRepository: MembersRepository,
        databaseRepository: DatabaseRepository,
        csvRepositories: List<CsvTransferableRepo>
    ): DataTransmissionUseCase =
        DataTransmissionUseCase(
            ctx,
            configuration,
            exportService,
            sessionManagerRepository,
            membersRepository,
            databaseRepository,
            csvRepositories
        )
}