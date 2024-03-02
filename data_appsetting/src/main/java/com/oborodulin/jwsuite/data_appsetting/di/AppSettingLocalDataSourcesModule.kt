package com.oborodulin.jwsuite.data_appsetting.di

import com.oborodulin.jwsuite.data_appsetting.local.db.repositories.sources.LocalAppSettingDataSource
import com.oborodulin.jwsuite.data_appsetting.sources.local.LocalAppSettingDataSourceImpl
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
//object AppSettingLocalDataSourcesModule {
abstract class AppSettingLocalDataSourcesModule {
    @Binds
    abstract fun bindLocalAppSettingDataSource(dataSourceImpl: LocalAppSettingDataSourceImpl): LocalAppSettingDataSource
    /*
        @Singleton
        @Provides
        fun provideAppSettingDataSource(
            appSettingDao: AppSettingDao, @IoDispatcher dispatcher: CoroutineDispatcher
        ): LocalAppSettingDataSource = LocalAppSettingDataSourceImpl(appSettingDao, dispatcher)
     */
}