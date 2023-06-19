package com.oborodulin.jwsuite.data.di

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data.local.db.dao.AppSettingDao
import com.oborodulin.jwsuite.data.local.db.dao.CongregationDao
import com.oborodulin.jwsuite.data.local.db.mappers.*
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationMappers
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationToCongregationEntityMapper
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationViewListToCongregationListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationViewToCongregationMapper
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingEntityListToAppSettingListMapper
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingEntityToAppSettingMapper
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingMappers
import com.oborodulin.jwsuite.data.local.db.mappers.appsetting.AppSettingToAppSettingEntityMapper
import com.oborodulin.jwsuite.data.local.db.repositories.*
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalAppSettingDataSource
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalCongregationDataSource
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalAppSettingDataSourceImpl
import com.oborodulin.jwsuite.data.local.db.sources.local.LocalCongregationDataSourceImpl
import com.oborodulin.jwsuite.domain.repositories.AppSettingsRepository
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    // MAPPERS:
    // AppSettings:
    @Singleton
    @Provides
    fun provideAppSettingEntityToAppSettingMapper(): AppSettingEntityToAppSettingMapper =
        AppSettingEntityToAppSettingMapper()

    @Singleton
    @Provides
    fun provideAppSettingToAppSettingEntityMapper(): AppSettingToAppSettingEntityMapper =
        AppSettingToAppSettingEntityMapper()

    @Singleton
    @Provides
    fun provideAppSettingEntityListToAppSettingListMapper(mapper: AppSettingEntityToAppSettingMapper): AppSettingEntityListToAppSettingListMapper =
        AppSettingEntityListToAppSettingListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun provideAppSettingMappers(
        appSettingEntityListToAppSettingListMapper: AppSettingEntityListToAppSettingListMapper,
        appSettingEntityToAppSettingMapper: AppSettingEntityToAppSettingMapper,
        appSettingToAppSettingEntityMapper: AppSettingToAppSettingEntityMapper
    ): AppSettingMappers = AppSettingMappers(
        appSettingEntityListToAppSettingListMapper,
        appSettingEntityToAppSettingMapper,
        appSettingToAppSettingEntityMapper
    )

    // Payers:
    @Singleton
    @Provides
    fun providePayerToPayerEntityMapper(): CongregationToCongregationEntityMapper = CongregationToCongregationEntityMapper()

    @Singleton
    @Provides
    fun providePayerEntityToPayerMapper(): CongregationViewToCongregationMapper = CongregationViewToCongregationMapper()

    @Singleton
    @Provides
    fun providePayerEntityListToPayerListMapper(mapper: CongregationViewToCongregationMapper): CongregationViewListToCongregationListMapper =
        CongregationViewListToCongregationListMapper(mapper = mapper)

    @Singleton
    @Provides
    fun providePayerMappers(
        congregationViewListToCongregationListMapper: CongregationViewListToCongregationListMapper,
        congregationViewToCongregationMapper: CongregationViewToCongregationMapper,
        congregationToCongregationEntityMapper: CongregationToCongregationEntityMapper
    ): CongregationMappers = CongregationMappers(
        congregationViewListToCongregationListMapper,
        congregationViewToCongregationMapper,
        congregationToCongregationEntityMapper
    )

    // DATA SOURCES:
    @Singleton
    @Provides
    fun provideAppSettingDataSource(
        appSettingDao: AppSettingDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalAppSettingDataSource = LocalAppSettingDataSourceImpl(appSettingDao, dispatcher)

    @Singleton
    @Provides
    fun providePayerDataSource(
        congregationDao: CongregationDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalCongregationDataSource = LocalCongregationDataSourceImpl(congregationDao, dispatcher)

    // REPOSITORIES:
    @Singleton
    @Provides
    fun provideAppSettingsRepository(
        localAppSettingDataSource: LocalAppSettingDataSource, mappers: AppSettingMappers
    ): AppSettingsRepository = AppSettingsRepositoryImpl(localAppSettingDataSource, mappers)

    @Singleton
    @Provides
    fun providePayersRepository(localCongregationDataSource: LocalCongregationDataSource, mappers: CongregationMappers):
            CongregationsRepository = CongregationsRepositoryImpl(localCongregationDataSource, mappers)
}