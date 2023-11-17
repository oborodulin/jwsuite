package com.oborodulin.jwsuite.data_appsetting.di

import android.content.Context
import com.oborodulin.jwsuite.data_appsetting.local.db.mappers.AppSettingEntityListToAppSettingListMapper
import com.oborodulin.jwsuite.data_appsetting.local.db.mappers.AppSettingEntityToAppSettingMapper
import com.oborodulin.jwsuite.data_appsetting.local.db.mappers.AppSettingMappers
import com.oborodulin.jwsuite.data_appsetting.local.db.mappers.AppSettingToAppSettingEntityMapper
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppSettingMappersModule {
    @Singleton
    @Provides
    fun provideAppSettingEntityToAppSettingMapper(@ApplicationContext ctx: Context): AppSettingEntityToAppSettingMapper =
        AppSettingEntityToAppSettingMapper(ctx = ctx)

    @Singleton
    @Provides
    fun provideAppSettingToAppSettingEntityMapper(): AppSettingToAppSettingEntityMapper =
        AppSettingToAppSettingEntityMapper()

    @Singleton
    @Provides
    fun provideAppSettingEntityListToAppSettingListMapper(mapper: AppSettingEntityToAppSettingMapper): AppSettingEntityListToAppSettingListMapper =
        AppSettingEntityListToAppSettingListMapper(
            mapper = mapper
        )

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
}