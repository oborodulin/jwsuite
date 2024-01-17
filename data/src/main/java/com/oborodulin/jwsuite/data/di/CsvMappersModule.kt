package com.oborodulin.jwsuite.data.di

import com.oborodulin.jwsuite.data_appsetting.local.csv.mappers.AppSettingEntityListToAppSettingCsvListMapper
import com.oborodulin.jwsuite.data_appsetting.local.csv.mappers.AppSettingEntityToAppSettingCsvMapper
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CsvMappersModule {
    @Singleton
    @Provides
    fun provideAppSettingEntityToAppSettingCsvMapper(): AppSettingEntityToAppSettingCsvMapper =
        AppSettingEntityToAppSettingCsvMapper()

    @Singleton
    @Provides
    fun provideAppSettingEntityListToAppSettingCsvListMapper(mapper: AppSettingEntityToAppSettingCsvMapper): AppSettingEntityListToAppSettingCsvListMapper =
        AppSettingEntityListToAppSettingCsvListMapper(
            mapper = mapper
        )

}