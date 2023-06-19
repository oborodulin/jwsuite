package com.oborodulin.jwsuite.data.di

import com.oborodulin.jwsuite.data.local.db.JwSuiteDatabase
import com.oborodulin.jwsuite.data.local.db.dao.AppSettingDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoRegionDao
import com.oborodulin.jwsuite.data.local.db.dao.CongregationDao
import com.oborodulin.jwsuite.data.local.db.dao.GroupDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoLocalityDistrictDao
import com.oborodulin.jwsuite.data.local.db.dao.GeoLocalityDao
import com.oborodulin.jwsuite.data.local.db.mappers.*
import com.oborodulin.jwsuite.data.local.db.repositories.*
import com.oborodulin.jwsuite.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    // PERSISTENCE
    // DAO:
    @Singleton
    @Provides
    fun provideAppSettingDao(db: JwSuiteDatabase): AppSettingDao = db.appSettingDao()

    @Singleton
    @Provides
    fun providePayerDao(db: JwSuiteDatabase): CongregationDao = db.territoryDao()

    @Singleton
    @Provides
    fun provideMeterDao(db: JwSuiteDatabase): GeoRegionDao = db.geoRegionDao()

    @Singleton
    @Provides
    fun provideServiceDao(db: JwSuiteDatabase): GeoLocalityDao = db.congregationDao()

    @Singleton
    @Provides
    fun provideRateDao(db: JwSuiteDatabase): GroupDao = db.groupDao()

    @Singleton
    @Provides
    fun provideReceiptDao(db: JwSuiteDatabase): GeoLocalityDistrictDao = db.memberDao()

}