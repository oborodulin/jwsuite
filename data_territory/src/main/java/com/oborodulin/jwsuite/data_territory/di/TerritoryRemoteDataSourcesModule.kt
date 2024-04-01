package com.oborodulin.jwsuite.data_territory.di

import com.oborodulin.jwsuite.data_territory.remote.osm.model.house.HouseService
import com.oborodulin.jwsuite.data_territory.remote.sources.RemoteHouseDataSource
import com.oborodulin.jwsuite.data_territory.sources.remote.RemoteHouseDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
//abstract class TerritoryRemoteDataSourcesModule {
interface TerritoryRemoteDataSourcesModule {
    /*@Binds
    abstract fun bindRemoteHouseDataSource(dataSourceImpl: RemoteHouseDataSourceImpl): RemoteHouseDataSource*/

    @Singleton
    @Provides
    fun provideRemoteHouseDataSource(houseService: HouseService): RemoteHouseDataSource =
        RemoteHouseDataSourceImpl(houseService = houseService)
}