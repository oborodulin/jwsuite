package com.oborodulin.jwsuite.data.di

import com.oborodulin.jwsuite.data.local.db.JwSuiteDatabase
import com.oborodulin.jwsuite.data.local.db.dao.DatabaseDao
import com.oborodulin.jwsuite.data_appsetting.local.db.dao.AppSettingDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.CongregationDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.GroupDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.MemberDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.TransferDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoLocalityDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoLocalityDistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoMicrodistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoRegionDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoRegionDistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoStreetDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.EntranceDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.FloorDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.HouseDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.RoomDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.TerritoryCategoryDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.TerritoryDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.TerritoryReportDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.TerritoryStreetDao
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
    fun provideDatabaseDao(db: JwSuiteDatabase): DatabaseDao = db.databaseDao()

    @Singleton
    @Provides
    fun provideAppSettingDao(db: JwSuiteDatabase): AppSettingDao = db.appSettingDao()

    // Geo:
    @Singleton
    @Provides
    fun provideGeoRegionDao(db: JwSuiteDatabase): GeoRegionDao = db.geoRegionDao()

    @Singleton
    @Provides
    fun provideGeoRegionDistrictDao(db: JwSuiteDatabase): GeoRegionDistrictDao =
        db.geoRegionDistrictDao()

    @Singleton
    @Provides
    fun provideGeoLocalityDao(db: JwSuiteDatabase): GeoLocalityDao = db.geoLocalityDao()

    @Singleton
    @Provides
    fun provideGeoLocalityDistrictDao(db: JwSuiteDatabase): GeoLocalityDistrictDao =
        db.geoLocalityDistrictDao()

    @Singleton
    @Provides
    fun provideGeoMicrodistrictDao(db: JwSuiteDatabase): GeoMicrodistrictDao =
        db.geoMicrodistrictDao()

    @Singleton
    @Provides
    fun provideGeoStreetDao(db: JwSuiteDatabase): GeoStreetDao = db.geoStreetDao()

    // Congregation:
    @Singleton
    @Provides
    fun provideCongregationDao(db: JwSuiteDatabase): CongregationDao = db.congregationDao()

    @Singleton
    @Provides
    fun provideGroupDao(db: JwSuiteDatabase): GroupDao = db.groupDao()

    @Singleton
    @Provides
    fun provideMemberDao(db: JwSuiteDatabase): MemberDao = db.memberDao()

    @Singleton
    @Provides
    fun provideTransferDao(db: JwSuiteDatabase): TransferDao = db.transferDao()

    // Territory:
    @Singleton
    @Provides
    fun provideTerritoryCategoryDao(db: JwSuiteDatabase): TerritoryCategoryDao =
        db.territoryCategoryDao()

    @Singleton
    @Provides
    fun provideTerritoryDao(db: JwSuiteDatabase): TerritoryDao = db.territoryDao()

    @Singleton
    @Provides
    fun provideHouseDao(db: JwSuiteDatabase): HouseDao = db.houseDao()

    @Singleton
    @Provides
    fun provideEntranceDao(db: JwSuiteDatabase): EntranceDao = db.entranceDao()

    @Singleton
    @Provides
    fun provideFloorDao(db: JwSuiteDatabase): FloorDao = db.floorDao()

    @Singleton
    @Provides
    fun provideRoomDao(db: JwSuiteDatabase): RoomDao = db.roomDao()

    @Singleton
    @Provides
    fun provideTerritoryStreetDao(db: JwSuiteDatabase): TerritoryStreetDao = db.territoryStreetDao()

    @Singleton
    @Provides
    fun provideTerritoryReportDao(db: JwSuiteDatabase): TerritoryReportDao = db.territoryReportDao()
}