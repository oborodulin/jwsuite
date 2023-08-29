package com.oborodulin.jwsuite.data.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.oborodulin.home.common.util.Mapper
import com.oborodulin.jwsuite.data.local.db.converters.JwSuiteTypeConverters
import com.oborodulin.jwsuite.data.local.db.dao.*
import com.oborodulin.jwsuite.data.local.db.entities.*
import com.oborodulin.jwsuite.data.local.db.views.*
import com.oborodulin.jwsuite.data.util.Constants
import com.oborodulin.jwsuite.data.util.Constants.DATABASE_PASSPHRASE
import com.oborodulin.jwsuite.data_congregation.local.db.dao.CongregationDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.GroupDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.MemberDao
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationMemberCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.FavoriteCongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.GroupView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoLocalityDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoLocalityDistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoMicrodistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoRegionDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoRegionDistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoStreetDao
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoDistrictStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoLocalityTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoMicrodistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionDistrictTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoRegionTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityDistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoLocalityView
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoMicrodistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionDistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoRegionView
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoStreetView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityDistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.LocalityView
import com.oborodulin.jwsuite.data_geo.local.db.views.MicrodistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionDistrictView
import com.oborodulin.jwsuite.data_geo.local.db.views.StreetView
import kotlinx.coroutines.*
import net.sqlcipher.database.SupportFactory
import timber.log.Timber
import java.time.OffsetDateTime
import java.util.*
import java.util.concurrent.Executors

private const val TAG = "JwSuiteDatabase"

@Database(
    entities = [AppSettingEntity::class,
        GeoRegionEntity::class, GeoRegionTlEntity::class,
        GeoRegionDistrictEntity::class, GeoRegionDistrictTlEntity::class,
        GeoLocalityEntity::class, GeoLocalityTlEntity::class,
        GeoLocalityDistrictEntity::class, GeoLocalityDistrictTlEntity::class,
        GeoMicrodistrictEntity::class, GeoMicrodistrictTlEntity::class,
        GeoStreetEntity::class, GeoStreetTlEntity::class, GeoDistrictStreetEntity::class,
        CongregationEntity::class, GroupEntity::class, MemberEntity::class,
        CongregationMemberCrossRefEntity::class, MemberMinistryEntity::class,
        TerritoryCategoryEntity::class, TerritoryEntity::class, TerritoryStreetEntity::class,
        TerritoryMemberCrossRefEntity::class,
        HouseEntity::class, EntranceEntity::class, FloorEntity::class, RoomEntity::class,
        TerritoryMemberReportEntity::class,
        CongregationTerritoryCrossRefEntity::class],
    views = [
        GeoRegionView::class, RegionDistrictView::class, LocalityView::class, LocalityDistrictView::class,
        MicrodistrictView::class, StreetView::class,
        GeoRegionDistrictView::class, GeoLocalityView::class,
        GeoLocalityDistrictView::class, GeoMicrodistrictView::class, GeoStreetView::class,
        CongregationView::class, FavoriteCongregationView::class, GroupView::class, MemberView::class,
        TerritoryMemberLastReceivingDateView::class, TerritoryPrivateSectorView::class,
        TerritoryView::class, TerritoryStreetView::class, TerritoryStreetHouseView::class,
        TerritoryStreetNamesAndHouseNumsView::class, TerritoryLocationView::class,
        TerritoriesHandOutView::class, TerritoriesAtWorkView::class, TerritoriesIdleView::class,
        HouseView::class, EntranceView::class,
        //TerritoryInfoView::class
    ],
    version = 1, exportSchema = true
)
@TypeConverters(JwSuiteTypeConverters::class)
abstract class JwSuiteDatabase : RoomDatabase() {
    abstract fun appSettingDao(): AppSettingDao

    // Geo:
    abstract fun geoRegionDao(): GeoRegionDao
    abstract fun geoRegionDistrictDao(): GeoRegionDistrictDao
    abstract fun geoLocalityDao(): GeoLocalityDao
    abstract fun geoLocalityDistrictDao(): GeoLocalityDistrictDao
    abstract fun geoMicrodistrictDao(): GeoMicrodistrictDao
    abstract fun geoStreetDao(): GeoStreetDao

    // Congregation:
    abstract fun congregationDao(): CongregationDao
    abstract fun groupDao(): GroupDao
    abstract fun memberDao(): MemberDao

    // Territory:
    abstract fun territoryCategoryDao(): TerritoryCategoryDao
    abstract fun territoryDao(): TerritoryDao
    abstract fun houseDao(): HouseDao
    abstract fun entranceDao(): EntranceDao
    abstract fun floorDao(): FloorDao
    abstract fun roomDao(): RoomDao

    companion object {
        var isImportDone: Deferred<Boolean>? = null

        @Volatile
        var isImportExecute: Boolean = false

        @Volatile
        private var INSTANCE: JwSuiteDatabase? = null

        @Synchronized
        fun getInstance(context: Context, jsonLogger: Gson? = Gson()): JwSuiteDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
            // Smart cast is only available to local variables.
            var instance = INSTANCE
            // If instance is `null` make a new database instance.
            if (instance == null) {
                instance =
                    Room.databaseBuilder(
                        context,
                        JwSuiteDatabase::class.java,
                        Constants.DATABASE_NAME
                    )//.openHelperFactory(SupportFactory(DATABASE_PASSPHRASE.toByteArray()))
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this lesson. You can learn more about
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        //.fallbackToDestructiveMigration()
                        .addCallback(DatabaseCallback(context, jsonLogger))
                        .build()
                // Assign INSTANCE to the newly created database.
                INSTANCE = instance
            }
            // Return instance; smart cast to be non-null.
            return instance
        }

        @Synchronized
        fun getTestInstance(context: Context, jsonLogger: Gson? = null): JwSuiteDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance =
                    Room.inMemoryDatabaseBuilder(context, JwSuiteDatabase::class.java)
                        //.addCallback(DatabaseCallback(context, jsonLogger))
                        .allowMainThreadQueries()
                        //https://stackoverflow.com/questions/57027850/testing-android-room-with-livedata-coroutines-and-transactions
                        .setTransactionExecutor(Executors.newSingleThreadExecutor())
                        .build()
                INSTANCE = instance
            }
            return instance
        }

        // https://androidexplained.github.io/android/room/2020/10/03/room-backup-restore.html
        @Synchronized
        fun getBackupInstance(context: Context, jsonLogger: Gson? = Gson()): JwSuiteDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
            // Smart cast is only available to local variables.
            var instance = INSTANCE
            // If instance is `null` make a new database instance.
            if (instance == null) {
                instance =
                    Room.databaseBuilder(
                        context,
                        JwSuiteDatabase::class.java,
                        Constants.DATABASE_NAME
                    ).openHelperFactory(SupportFactory(DATABASE_PASSPHRASE.toByteArray()))
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this lesson. You can learn more about
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        //.fallbackToDestructiveMigration()
                        .addCallback(DatabaseCallback(context, jsonLogger))
                        .setJournalMode(JournalMode.TRUNCATE)
                        .build()
                // Assign INSTANCE to the newly created database.
                INSTANCE = instance
            }
            // Return instance; smart cast to be non-null.
            return instance
        }

        // https://stackoverflow.com/questions/2421189/version-of-sqlite-used-in-android
        fun sqliteVersion(): String? = SQLiteDatabase.create(null).use {
            android.database.DatabaseUtils.stringForQuery(it, "SELECT sqlite_version()", null)
        }

        @Synchronized
        fun close() {
            INSTANCE?.close()
            INSTANCE = null
        }
    }

    /**
     * https://stackoverflow.com/questions/5955202/how-to-remove-database-from-emulator
     */
    class DatabaseCallback(private val context: Context, private val jsonLogger: Gson? = null) :
        Callback() {
        private val currentDateTime: OffsetDateTime = OffsetDateTime.now()

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            isImportExecute = true
            Timber.tag(TAG)
                .i("Database onCreate() called on thread '%s':", Thread.currentThread().name)
            // moving to a new thread
            // Executors.newSingleThreadExecutor().execute{f}
            //GlobalScope.launch(Dispatchers.Main)
            //CoroutineScope(Dispatchers.IO).launch()
            isImportDone = CoroutineScope(Dispatchers.IO).async {
                Timber.tag(TAG)
                    .i("Start thread '%s': prePopulateDb(...)", Thread.currentThread().name)
                prePopulateDb(db)
                true
            }
            Timber.tag(TAG)
                .i("Database onCreate() ended on thread '%s':", Thread.currentThread().name)
        }

        private fun prePopulateDb(db: SupportSQLiteDatabase) {
            Timber.tag(TAG).i("prePopulateDb(...) called")
            db.beginTransaction()
            try {
                // Default settings:
                insertDefAppSettings(db)

                // ==============================
                // GEO:
                // Default regions:
                val donRegion = insertDefRegion(db, GeoRegionEntity.donetskRegion(context))
                val lugRegion = insertDefRegion(db, GeoRegionEntity.luganskRegion(context))

                // DON districts:
                val maryinskyDistrict = insertDefRegionDistrict(
                    db, GeoRegionDistrictEntity.maryinskyRegionDistrict(context, donRegion.regionId)
                )

                val donetskyDistrict = insertDefRegionDistrict(
                    db, GeoRegionDistrictEntity.donetskyRegionDistrict(context, donRegion.regionId)
                )

                // DON localities:
                val donetsk = insertDeftLocality(
                    db, GeoLocalityEntity.donetskLocality(context, donRegion.regionId)
                )
                val makeevka = insertDeftLocality(
                    db, GeoLocalityEntity.makeevkaLocality(context, donRegion.regionId)
                )
                val marinka = insertDeftLocality(
                    db, GeoLocalityEntity.marinkaLocality(
                        context, donRegion.regionId, maryinskyDistrict.regionDistrictId
                    )
                )
                val mospino = insertDeftLocality(
                    db, GeoLocalityEntity.mospinoLocality(
                        context, donRegion.regionId, donetskyDistrict.regionDistrictId
                    )
                )
                // LUG localities:
                val lugansk = insertDeftLocality(
                    db, GeoLocalityEntity.luganskLocality(context, lugRegion.regionId)
                )

                // Donetsk districts:
                val budyonovsky = insertDeftLocalityDistrict(
                    db,
                    GeoLocalityDistrictEntity.bdnLocalityDistrict(context, donetsk.localityId)
                )
                val kievsky = insertDeftLocalityDistrict(
                    db,
                    GeoLocalityDistrictEntity.kvkLocalityDistrict(context, donetsk.localityId)
                )
                val kuybyshevsky = insertDeftLocalityDistrict(
                    db,
                    GeoLocalityDistrictEntity.kbshLocalityDistrict(context, donetsk.localityId)
                )
                val voroshilovsky = insertDeftLocalityDistrict(
                    db,
                    GeoLocalityDistrictEntity.vrshLocalityDistrict(context, donetsk.localityId)
                )

                // Donetsk microdistricts:
                val donskoy = insertDefMicrodistrict(
                    db,
                    GeoMicrodistrictEntity.donskoyMicrodistrict(
                        context, budyonovsky.ldLocalitiesId, budyonovsky.localityDistrictId
                    )
                )
                val cvetochny = insertDefMicrodistrict(
                    db,
                    GeoMicrodistrictEntity.cvetochnyMicrodistrict(
                        context, budyonovsky.ldLocalitiesId, budyonovsky.localityDistrictId
                    )
                )

                // Donetsk streets:
                val strelkovojDivizii = insertDefStreet(
                    db, GeoStreetEntity.strelkovojDiviziiStreet(context, donetsk.localityId)
                )
                insertDefStreetDistrict(db, strelkovojDivizii, budyonovsky, donskoy)
                insertDefStreetDistrict(db, strelkovojDivizii, budyonovsky, cvetochny)
                val nezavisimosti = insertDefStreet(
                    db, GeoStreetEntity.nezavisimostiStreet(context, donetsk.localityId)
                )
                insertDefStreetDistrict(db, nezavisimosti, budyonovsky, donskoy)
                val baratynskogo = insertDefStreet(
                    db, GeoStreetEntity.baratynskogoStreet(context, donetsk.localityId)
                )
                insertDefStreetDistrict(db, baratynskogo, budyonovsky)
                val patorgynskogo = insertDefStreet(
                    db, GeoStreetEntity.patorgynskogoStreet(context, donetsk.localityId)
                )
                insertDefStreetDistrict(db, patorgynskogo, budyonovsky)
                val belogorodskaya = insertDefStreet(
                    db, GeoStreetEntity.belogorodskayaStreet(context, donetsk.localityId)
                )
                insertDefStreetDistrict(db, belogorodskaya, budyonovsky)

                // ==============================
                // Houses strelkovoj divizii:
                val sd7b = insertDefHouse(
                    db, HouseEntity.defaultHouse(
                        streetId = strelkovojDivizii.streetId,
                        localityDistrictId = budyonovsky.localityDistrictId,
                        houseNum = 7, houseLetter = "б", buildingNum = 1,
                    )
                )
                val sd9a = insertDefHouse(
                    db, HouseEntity.defaultHouse(
                        streetId = strelkovojDivizii.streetId,
                        localityDistrictId = budyonovsky.localityDistrictId,
                        microdistrictId = donskoy.microdistrictId,
                        houseNum = 9, houseLetter = "а", isBusiness = true
                    )
                )
                val sd21 = insertDefHouse(
                    db, HouseEntity.defaultHouse(
                        streetId = strelkovojDivizii.streetId,
                        localityDistrictId = budyonovsky.localityDistrictId,
                        microdistrictId = cvetochny.microdistrictId,
                        houseNum = 21
                    )
                )
                // Houses nezavisimosti:
                val n8 = insertDefHouse(
                    db, HouseEntity.defaultHouse(
                        streetId = nezavisimosti.streetId,
                        localityDistrictId = budyonovsky.localityDistrictId,
                        microdistrictId = donskoy.microdistrictId,
                        houseNum = 8
                    )
                )
                val n14 = insertDefHouse(
                    db, HouseEntity.defaultHouse(
                        streetId = strelkovojDivizii.streetId,
                        localityDistrictId = budyonovsky.localityDistrictId,
                        microdistrictId = donskoy.microdistrictId,
                        houseNum = 14
                    )
                )
                val n29v = insertDefHouse(
                    db, HouseEntity.defaultHouse(
                        streetId = strelkovojDivizii.streetId,
                        localityDistrictId = budyonovsky.localityDistrictId,
                        microdistrictId = cvetochny.microdistrictId,
                        houseNum = 29, houseLetter = "в", isBusiness = true
                    )
                )

                // Mospino streets:
                val novomospino = insertDefStreet(
                    db, GeoStreetEntity.novomospinoStreet(context, mospino.localityId)
                )
                val gertsena =
                    insertDefStreet(db, GeoStreetEntity.gertsenaStreet(context, mospino.localityId))


                // ==============================
                // CONGREGATION:
                // Default congregations:
                // 1
                val congregation1 = CongregationEntity.favoriteCongregation(
                    context, donetsk.localityId
                )
                db.insert(
                    CongregationEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(congregation1)
                )
                Timber.tag(TAG).i("CONGREGATION: Default 1 Congregation imported")
                jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.toJson(congregation1)) }
                // 2
                val congregation2 = CongregationEntity.secondCongregation(
                    context, donetsk.localityId
                )
                db.insert(
                    CongregationEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(congregation2)
                )
                Timber.tag(TAG).i("CONGREGATION: Default 2 Congregation imported")
                jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.toJson(congregation2)) }

                // Default Groups:
                val group11 = insertDefGroup(db, 1, congregation1)
                val group12 = insertDefGroup(db, 2, congregation1)
                val group13 = insertDefGroup(db, 3, congregation1)
                val group14 = insertDefGroup(db, 4, congregation1)
                val group15 = insertDefGroup(db, 5, congregation1)
                val group16 = insertDefGroup(db, 6, congregation1)
                val group17 = insertDefGroup(db, 7, congregation1)

                // Group members:
                val ivanov = insertDefMember(db, 1, congregation1, group11)
                val petrov = insertDefMember(db, 2, congregation1, group11)
                val sidorov = insertDefMember(db, 1, congregation1, group12)

                // ==============================
                // TERRITORY:
                // Categories:
                val houseTerritoryCategory = insertDefTerritoryCategory(
                    db, TerritoryCategoryEntity.houseTerritoryCategory(context)
                )
                val floorTerritoryCategory = insertDefTerritoryCategory(
                    db, TerritoryCategoryEntity.floorTerritoryCategory(context)
                )
                val roomTerritoryCategory = insertDefTerritoryCategory(
                    db, TerritoryCategoryEntity.roomTerritoryCategory(context)
                )
                // Room territories:
                val roomTerrs1 = insertDefTerritories(
                    db, roomTerritoryCategory, congregation1, mospino, budyonovsky, donskoy
                )

                // House territories (private sector):
                val houseTerrs1 = insertDefTerritories(
                    db, houseTerritoryCategory, congregation1, mospino, budyonovsky, cvetochny
                )
                insertDefTerritoryStreet(
                    db, houseTerrs1.first { it.territoryNum == 1 }, baratynskogo
                )
                insertDefTerritoryStreet(
                    db, houseTerrs1.first { it.territoryNum == 1 }, patorgynskogo, isEven = false
                )
                insertDefTerritoryStreet(
                    db, houseTerrs1.first { it.territoryNum == 2 }, belogorodskaya, isEven = true
                )

                // Territory members:
                // ivanov
                insertDefTerritoryMember(db, roomTerrs1.first { it.territoryNum == 1 }, ivanov)
                // receive & delivery
                val roomTerrNum2 = roomTerrs1.first { it.territoryNum == 2 }
                insertDefTerritoryMember(db, roomTerrNum2, ivanov)
                val territoryMember = insertDefTerritoryMember(db, roomTerrNum2, ivanov)
                deliveryDefTerritoryMember(db, roomTerrNum2, territoryMember)

                insertDefTerritoryMember(db, roomTerrs1.first { it.territoryNum == 3 }, ivanov)
                insertDefTerritoryMember(db, houseTerrs1.first { it.territoryNum == 1 }, ivanov)
                // petrov
                insertDefTerritoryMember(db, roomTerrs1.first { it.territoryNum == 4 }, petrov)
                // sidorov
                insertDefTerritoryMember(db, houseTerrs1.first { it.territoryNum == 2 }, sidorov)

                // ==============================
                db.setTransactionSuccessful()
                Timber.tag(TAG).i("prePopulateDb(...) successful ended")
                /*
                            val isImport: Boolean = true
                            if (isImport) {
                            instance?.payerDao()?.add(payerEntity)
                            }

                 */
            } catch (e: SQLiteException) {
                Timber.tag(TAG).e(e)
            } finally {
                db.endTransaction()
                isImportExecute = false
            }
        }

        private fun insertDefAppSettings(db: SupportSQLiteDatabase) {
            // Lang
            val lang = AppSettingEntity.langParam()
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(lang)
            )
            // Currency Code
            val currencyCode = AppSettingEntity.currencyCodeParam()
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(currencyCode)
            )
            // All Items
            val allItems = AppSettingEntity.allItemsParam(context)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(allItems)
            )
            // Day Mu
            val dayMu = AppSettingEntity.dayMuParam(context)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(dayMu)
            )
            // Month Mu
            val monthMu = AppSettingEntity.monthMuParam(context)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(monthMu)
            )
            // Year Mu
            val yearMu = AppSettingEntity.yearMuParam(context)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(monthMu)
            )
            // Person Num MU
            val personNumMu = AppSettingEntity.personNumMuParam(context)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(personNumMu)
            )
            // Territory Business Mark
            val territoryBusinessMark =
                AppSettingEntity.territoryBusinessMarkParam(context)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryBusinessMark)
            )
            // Territory Processing Period
            val territoryProcessingPeriod =
                AppSettingEntity.territoryProcessingPeriodParam(context)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryProcessingPeriod)
            )
            // Territory At Hand Period
            val territoryAtHandPeriod = AppSettingEntity.territoryAtHandPeriodParam(context)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryAtHandPeriod)
            )
            // Territory Rooms Limit
            val territoryRoomsLimit = AppSettingEntity.territoryRoomsLimitParam(context)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryRoomsLimit)
            )
            // Territory Max Rooms
            val territoryMaxRoomsParam = AppSettingEntity.territoryMaxRoomsParam(context)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryMaxRoomsParam)
            )
            // Territory Idle Period
            val territoryIdlePeriod = AppSettingEntity.territoryIdlePeriodParam(context)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryIdlePeriod)
            )
            Timber.tag(TAG).i("Default app parameters imported")
            jsonLogger?.let {
                Timber.tag(TAG)
                    .i(
                        ": {\"params\": {\"lang\": {%s}, \"currencyCode\": {%s}, \"allItems\": {%s}, \"dayMu\": {%s}, \"monthMu\": {%s}, \"yearMu\": {%s}, \"personNumMu\": {%s}, \"territoryBusinessMark\": {%s}, \"territoryProcessingPeriod\": {%s}, \"territoryAtHandPeriod\": {%s}, \"territoryRoomsLimit\": {%s}, \"territoryMaxRoomsParam\": {%s}, \"territoryIdlePeriod\": {%s}}",
                        it.toJson(lang),
                        it.toJson(currencyCode),
                        it.toJson(allItems),
                        it.toJson(dayMu),
                        it.toJson(monthMu),
                        it.toJson(yearMu),
                        it.toJson(personNumMu),
                        it.toJson(territoryBusinessMark), it.toJson(territoryProcessingPeriod),
                        it.toJson(territoryAtHandPeriod), it.toJson(territoryRoomsLimit),
                        it.toJson(territoryMaxRoomsParam), it.toJson(territoryIdlePeriod)
                    )
            }
        }

        private fun insertDefGroup(
            db: SupportSQLiteDatabase, groupNum: Int, congregation: CongregationEntity
        ): GroupEntity {
            var group: GroupEntity? = null
            when (groupNum) {
                1 -> group = GroupEntity.group1(context, congregation.congregationId)
                2 -> group = GroupEntity.group2(context, congregation.congregationId)
                3 -> group = GroupEntity.group3(context, congregation.congregationId)
                4 -> group = GroupEntity.group4(context, congregation.congregationId)
                5 -> group = GroupEntity.group5(context, congregation.congregationId)
                6 -> group = GroupEntity.group6(context, congregation.congregationId)
                7 -> group = GroupEntity.group7(context, congregation.congregationId)
                8 -> group = GroupEntity.group8(context, congregation.congregationId)
                9 -> group = GroupEntity.group9(context, congregation.congregationId)
                10 -> group = GroupEntity.group10(context, congregation.congregationId)
                11 -> group = GroupEntity.group11(context, congregation.congregationId)
                12 -> group = GroupEntity.group12(context, congregation.congregationId)
            }
            group?.let {
                db.insert(
                    GroupEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(it)
                )
                Timber.tag(TAG).i("CONGREGATION: Default group imported")
                jsonLogger?.let { logger ->
                    Timber.tag(TAG).i(": {\"group\": {%s}}", logger.toJson(it))
                }
            }
            return group!!
        }

        private fun insertDefMember(
            db: SupportSQLiteDatabase, memberNumInGroup: Int,
            congregation: CongregationEntity, group: GroupEntity
        ): MemberEntity {
            var member: MemberEntity? = null
            when (congregation.congregationNum) {
                "1" -> when (group.groupNum) {
                    1 -> when (memberNumInGroup) {
                        1 -> member = MemberEntity.ivanovMember11(context, group.groupId)
                        2 -> member = MemberEntity.petrovMember12(context, group.groupId)
                    }

                    2 -> when (memberNumInGroup) {
                        1 -> member = MemberEntity.sidorovMember21(context, group.groupId)
                    }
                }

                "2" -> when (group.groupNum) {
                    1 -> when (memberNumInGroup) {
                        1 -> member = MemberEntity.tarasovaMember11(context, group.groupId)
                        2 -> member = MemberEntity.shevchukMember12(context, group.groupId)
                    }

                    2 -> when (memberNumInGroup) {
                        1 -> member = MemberEntity.matveychukMember21(context, group.groupId)
                    }
                }
            }
            member?.let {
                db.insert(
                    MemberEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(it)
                )
                val congregationMember = CongregationMemberCrossRefEntity.defaultCongregationMember(
                    congregationId = congregation.congregationId, memberId = it.memberId
                )
                db.insert(
                    CongregationMemberCrossRefEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(congregationMember)
                )
                Timber.tag(TAG).i("CONGREGATION: Default member imported")
                jsonLogger?.let { logger ->
                    Timber.tag(TAG).i(
                        ": {\"member\": {%s}, \"congregationMember\": {%s}}",
                        logger.toJson(it),
                        logger.toJson(congregationMember)
                    )
                }
            }
            return member!!
        }

        private fun insertDefRegion(db: SupportSQLiteDatabase, region: GeoRegionEntity):
                GeoRegionEntity {
            val textContent =
                GeoRegionTlEntity.regionTl(context, region.regionCode, region.regionId)
            db.insert(
                GeoRegionEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(region)
            )
            db.insert(
                GeoRegionTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(textContent)
            )
            Timber.tag(TAG).i("GEO: Default region imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"region\": {%s}, \"tl\": {%s}}", it.toJson(region), it.toJson(textContent)
                )
            }
            return region
        }

        private fun insertDefRegionDistrict(
            db: SupportSQLiteDatabase, regionDistrict: GeoRegionDistrictEntity
        ): GeoRegionDistrictEntity {
            val textContent =
                GeoRegionDistrictTlEntity.regionDistrictTl(
                    context,
                    regionDistrict.regDistrictShortName, regionDistrict.regionDistrictId
                )
            db.insert(
                GeoRegionDistrictEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(regionDistrict)
            )
            db.insert(
                GeoRegionDistrictTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(textContent)
            )
            Timber.tag(TAG).i("GEO: Default region District imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"regionDistrict\": {%s}, \"tl\": {%s}}",
                    it.toJson(regionDistrict), it.toJson(textContent)
                )
            }
            return regionDistrict
        }

        private fun insertDeftLocality(db: SupportSQLiteDatabase, locality: GeoLocalityEntity):
                GeoLocalityEntity {
            val textContent =
                GeoLocalityTlEntity.localityTl(context, locality.localityCode, locality.localityId)
            db.insert(
                GeoLocalityEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(locality)
            )
            db.insert(
                GeoLocalityTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(textContent)
            )
            Timber.tag(TAG).i("GEO: Default locality imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"locality\": {%s}, \"tl\": {%s}}",
                    it.toJson(locality), it.toJson(textContent)
                )
            }
            return locality
        }

        private fun insertDeftLocalityDistrict(
            db: SupportSQLiteDatabase, localityDistrict: GeoLocalityDistrictEntity
        ):
                GeoLocalityDistrictEntity {
            val textContent =
                GeoLocalityDistrictTlEntity.localityDistrictTl(
                    context,
                    localityDistrict.locDistrictShortName, localityDistrict.localityDistrictId
                )
            db.insert(
                GeoLocalityDistrictEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(localityDistrict)
            )
            db.insert(
                GeoLocalityDistrictTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(textContent)
            )
            Timber.tag(TAG).i("GEO: Default locality district imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"localityDistrict\": {%s}, \"tl\": {%s}}",
                    it.toJson(localityDistrict), it.toJson(textContent)
                )
            }
            return localityDistrict
        }

        private fun insertDefMicrodistrict(
            db: SupportSQLiteDatabase, microdistrict: GeoMicrodistrictEntity
        ): GeoMicrodistrictEntity {
            val textContent = GeoMicrodistrictTlEntity.microdistrictTl(
                context,
                microdistrict.microdistrictShortName, microdistrict.microdistrictId
            )
            db.insert(
                GeoMicrodistrictEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(microdistrict)
            )
            db.insert(
                GeoMicrodistrictTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(textContent)
            )
            Timber.tag(TAG).i("GEO: Default locality microdistrict imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"microdistrict\": {%s}, \"tl\": {%s}}",
                    it.toJson(microdistrict), it.toJson(textContent)
                )
            }
            return microdistrict
        }

        private fun insertDefStreet(db: SupportSQLiteDatabase, street: GeoStreetEntity):
                GeoStreetEntity {
            val textContent = GeoStreetTlEntity.streetTl(
                context, street.streetHashCode, street.streetId
            )
            db.insert(
                GeoStreetEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(street)
            )
            db.insert(
                GeoStreetTlEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(textContent)
            )
            Timber.tag(TAG).i("GEO: Default street imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"street\": {%s}, \"tl\": {%s}}",
                    it.toJson(street), it.toJson(textContent)
                )
            }
            return street
        }

        private fun insertDefStreetDistrict(
            db: SupportSQLiteDatabase, street: GeoStreetEntity,
            localityDistrict: GeoLocalityDistrictEntity,
            microdistrict: GeoMicrodistrictEntity? = null
        ) {
            val streetDistrict = GeoDistrictStreetEntity.defaultDistrictStreet(
                streetId = street.streetId,
                localityDistrictId = localityDistrict.localityDistrictId,
                microdistrictId = microdistrict?.microdistrictId
            )
            db.insert(
                GeoDistrictStreetEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(streetDistrict)
            )
            Timber.tag(TAG).i("GEO: Default street district imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.toJson(streetDistrict)) }
        }

        private fun insertDefHouse(db: SupportSQLiteDatabase, house: HouseEntity): HouseEntity {
            db.insert(
                HouseEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(house)
            )
            Timber.tag(TAG).i("GEO: Default house imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.toJson(house)) }
            return house
        }

        private fun insertDefTerritoryCategory(
            db: SupportSQLiteDatabase, territoryCategory: TerritoryCategoryEntity
        ): TerritoryCategoryEntity {
            db.insert(
                TerritoryCategoryEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryCategory)
            )
            Timber.tag(TAG).i("TERRITORY: Default territory category imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.toJson(territoryCategory)) }
            return territoryCategory
        }

        private fun insertDefTerritories(
            db: SupportSQLiteDatabase, territoryCategory: TerritoryCategoryEntity,
            congregation: CongregationEntity,
            locality: GeoLocalityEntity, localityDistrict: GeoLocalityDistrictEntity,
            microdistrict: GeoMicrodistrictEntity
        ): MutableList<TerritoryEntity> {
            val territories = mutableListOf<TerritoryEntity>()
            for (num in 1..144) {
                val territory = TerritoryEntity.defaultTerritory(
                    territoryCategoryId = territoryCategory.territoryCategoryId,
                    congregationId = congregation.congregationId,
                    localityId = when (num % 2) {
                        0 -> congregation.cLocalitiesId
                        else -> locality.localityId
                    },
                    localityDistrictId = when (num % 4) {
                        0 -> localityDistrict.localityDistrictId
                        else -> null
                    },
                    microdistrictId = when (num % 6) {
                        0 -> microdistrict.microdistrictId
                        else -> null
                    },
                    isBusiness = num % 8 == 0,
                    isInPerimeter = num % 10 == 0,
                    isGroupMinistry = num % 20 == 0,
                    territoryNum = num
                )
                db.insert(
                    TerritoryEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(territory)
                )
                val congregationTerritory =
                    CongregationTerritoryCrossRefEntity.defaultCongregationTerritory(
                        congregationId = congregation.congregationId,
                        territoryId = territory.territoryId
                    )
                db.insert(
                    CongregationTerritoryCrossRefEntity.TABLE_NAME,
                    SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(congregationTerritory)
                )
                Timber.tag(TAG).i("TERRITORY: Default territory imported")
                jsonLogger?.let {
                    Timber.tag(TAG).i(
                        ": {\"territory\": {%s}, \"congregationTerritory\": {%s}}",
                        it.toJson(territory), it.toJson(congregationTerritory)
                    )
                }
                territories.add(territory)
            }
            return territories
        }

        private fun insertDefTerritoryMember(
            db: SupportSQLiteDatabase, territory: TerritoryEntity, member: MemberEntity
        ): TerritoryMemberCrossRefEntity {
            val territoryMember = TerritoryMemberCrossRefEntity.defaultTerritoryMember(
                territoryId = territory.territoryId, memberId = member.memberId,
            )
            db.insert(
                TerritoryMemberCrossRefEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryMember)
            )
            val handOutTerritory = territory.copy(isProcessed = false)
            db.update(
                TerritoryEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(handOutTerritory), "territoryId = ?",
                Array(1) { handOutTerritory.territoryId.toString() }
            )
            Timber.tag(TAG).i("TERRITORY: Default territory member imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.toJson(territoryMember)) }
            Timber.tag(TAG).i("TERRITORY: Default hand out territory imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.toJson(handOutTerritory)) }
            return territoryMember
        }

        private fun deliveryDefTerritoryMember(
            db: SupportSQLiteDatabase, territory: TerritoryEntity,
            territoryMember: TerritoryMemberCrossRefEntity
        ) {
            val deliveryTerritoryMember = territoryMember.copy(deliveryDate = OffsetDateTime.now())
            db.update(
                TerritoryMemberCrossRefEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(deliveryTerritoryMember), "territoryMemberId = ?",
                Array(1) { territoryMember.territoryMemberId.toString() }
            )
            val idleTerritory = territory.copy(isProcessed = true)
            db.update(
                TerritoryEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(idleTerritory), "territoryId = ?",
                Array(1) { idleTerritory.territoryId.toString() }
            )
            Timber.tag(TAG).i("TERRITORY: Delivery imported territory member")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.toJson(deliveryTerritoryMember)) }
            Timber.tag(TAG).i("TERRITORY: Delivery imported territory")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.toJson(idleTerritory)) }
        }

        private fun insertDefTerritoryStreet(
            db: SupportSQLiteDatabase, territory: TerritoryEntity, street: GeoStreetEntity,
            isEven: Boolean? = null, isPrivateSector: Boolean? = null
        ) {
            val territoryStreet = TerritoryStreetEntity.privateSectorTerritoryStreet(
                territoryId = territory.territoryId, streetId = street.streetId, isEven = isEven
            )
            db.insert(
                TerritoryStreetEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryStreet)
            )
            Timber.tag(TAG).i("TERRITORY: Default territory street imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.toJson(territoryStreet)) }
        }
    }
}