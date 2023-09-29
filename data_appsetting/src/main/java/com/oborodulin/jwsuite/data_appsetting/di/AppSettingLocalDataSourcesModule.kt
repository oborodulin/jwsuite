package com.oborodulin.jwsuite.data_appsetting.di

import com.oborodulin.home.common.di.IoDispatcher
import com.oborodulin.jwsuite.data_appsetting.local.db.dao.AppSettingDao
import com.oborodulin.jwsuite.data_appsetting.local.db.repositories.sources.LocalAppSettingDataSource
import com.oborodulin.jwsuite.data_appsetting.local.db.sources.local.LocalAppSettingDataSourceImpl
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppSettingLocalDataSourcesModule {
    @Singleton
    @Provides
    fun provideAppSettingDataSource(
        appSettingDao: AppSettingDao, @IoDispatcher dispatcher: CoroutineDispatcher
    ): LocalAppSettingDataSource = LocalAppSettingDataSourceImpl(
        appSettingDao,
        dispatcher
    )
}