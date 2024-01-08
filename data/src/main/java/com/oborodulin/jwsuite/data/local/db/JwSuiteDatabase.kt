package com.oborodulin.jwsuite.data.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.oborodulin.home.common.util.LogLevel.LOG_DATABASE
import com.oborodulin.home.common.util.LogLevel.LOG_SECURE
import com.oborodulin.home.common.util.Mapper
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.data.local.db.converters.JwSuiteTypeConverters
import com.oborodulin.jwsuite.data.local.db.entities.MemberMinistryEntity
import com.oborodulin.jwsuite.data.util.Constants
import com.oborodulin.jwsuite.data.util.Constants.DATABASE_PASSPHRASE
import com.oborodulin.jwsuite.data_appsetting.local.db.dao.AppSettingDao
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data_congregation.local.db.dao.CongregationDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.GroupDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.MemberDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.TransferDao
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.TransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationTotalView
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.FavoriteCongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.GroupView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberMovementView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import com.oborodulin.jwsuite.data_congregation.local.db.views.RoleTransferObjectView
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoLocalityDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoLocalityDistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoMicrodistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoRegionDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoRegionDistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoStreetDao
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
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoStreetDistrictEntity
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
import com.oborodulin.jwsuite.data_territory.local.db.dao.EntranceDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.FloorDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.HouseDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.RoomDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.TerritoryCategoryDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.TerritoryDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.TerritoryReportDao
import com.oborodulin.jwsuite.data_territory.local.db.dao.TerritoryStreetDao
import com.oborodulin.jwsuite.data_territory.local.db.entities.CongregationTerritoryCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.EntranceEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryCategoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberCrossRefEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryMemberReportEntity
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.data_territory.local.db.views.EntranceView
import com.oborodulin.jwsuite.data_territory.local.db.views.FloorView
import com.oborodulin.jwsuite.data_territory.local.db.views.HouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.RoomView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoriesAtWorkView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoriesHandOutView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoriesIdleView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryLocationView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberLastReceivingDateView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberReportView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryPrivateSectorView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportHouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportRoomView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportStreetView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetHouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetNamesAndHouseNumsView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryView
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.domain.types.MemberType
import com.oborodulin.jwsuite.domain.types.TransferObjectType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.sqlcipher.database.SupportFactory
import timber.log.Timber
import java.time.OffsetDateTime
import java.util.concurrent.Executors

// https://stackoverflow.com/questions/65043370/type-mismatch-when-serializing-data-class
//import kotlinx.serialization.encodeToString

private const val TAG = "JwSuiteDatabase"

@Database(
    entities = [AppSettingEntity::class, RoleEntity::class,
        GeoRegionEntity::class, GeoRegionTlEntity::class,
        GeoRegionDistrictEntity::class, GeoRegionDistrictTlEntity::class,
        GeoLocalityEntity::class, GeoLocalityTlEntity::class,
        GeoLocalityDistrictEntity::class, GeoLocalityDistrictTlEntity::class,
        GeoMicrodistrictEntity::class, GeoMicrodistrictTlEntity::class,
        GeoStreetEntity::class, GeoStreetTlEntity::class, GeoStreetDistrictEntity::class,
        CongregationEntity::class, GroupEntity::class, MemberEntity::class, MemberMovementEntity::class,
        MemberRoleEntity::class, MemberCongregationCrossRefEntity::class, MemberMinistryEntity::class,
        TerritoryCategoryEntity::class, TerritoryEntity::class, TerritoryStreetEntity::class,
        TransferObjectEntity::class, RoleTransferObjectEntity::class,
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
        MemberRoleView::class, MemberMovementView::class, CongregationTotalView::class,
        TerritoryMemberLastReceivingDateView::class, TerritoryPrivateSectorView::class,
        TerritoryView::class, TerritoryStreetView::class, TerritoryStreetHouseView::class,
        TerritoryStreetNamesAndHouseNumsView::class, TerritoryLocationView::class,
        TerritoriesHandOutView::class, TerritoriesAtWorkView::class, TerritoriesIdleView::class,
        HouseView::class, EntranceView::class, FloorView::class, RoomView::class,
        RoleTransferObjectView::class, MemberRoleTransferObjectView::class,
        TerritoryMemberReportView::class, TerritoryReportStreetView::class,
        TerritoryReportHouseView::class, TerritoryReportRoomView::class
        //TerritoryInfoView::class
    ],
    version = 1, exportSchema = true
)
@TypeConverters(JwSuiteTypeConverters::class)
abstract class JwSuiteDatabase : RoomDatabase() {
    // DAOs:
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
    abstract fun transferDao(): TransferDao

    // Territory:
    abstract fun territoryCategoryDao(): TerritoryCategoryDao
    abstract fun territoryDao(): TerritoryDao
    abstract fun houseDao(): HouseDao
    abstract fun entranceDao(): EntranceDao
    abstract fun floorDao(): FloorDao
    abstract fun roomDao(): RoomDao
    abstract fun territoryStreetDao(): TerritoryStreetDao
    abstract fun territoryReportDao(): TerritoryReportDao

    companion object {
        var importJob: Deferred<Boolean>? = null

        @Volatile
        var isImportExecute: Boolean = false

        @Volatile
        private var INSTANCE: JwSuiteDatabase? = null

        //@Synchronized
        fun getInstance(
            ctx: Context, jsonLogger: Json? = Json,
            localSessionManagerDataSource: LocalSessionManagerDataSource
        ): JwSuiteDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            // https://developermemos.com/posts/synchronized-methods-kotlin
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE
                // DATABASE_PASSPHRASE.toByteArray()
                var databasePassphrase = ""
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    if (LOG_SECURE) Timber.tag(TAG).d("databasePassphrase getting")
                    runBlocking {
                        // https://stackoverflow.com/questions/57088428/kotlin-flow-how-to-unsubscribe-stop
                        //.takeWhile { it.isNotEmpty() }.collect {= it}
                        databasePassphrase =
                            localSessionManagerDataSource.databasePassphrase().first()
                    }
                    if (LOG_SECURE) Timber.tag(TAG)
                        .d("databasePassphrase got: %s", databasePassphrase)
                    val roomBuilder = Room.databaseBuilder(
                        ctx,
                        JwSuiteDatabase::class.java,
                        Constants.DATABASE_NAME
                    )// Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this lesson. You can learn more about
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        //.fallbackToDestructiveMigration()
                        .addCallback(
                            DatabaseCallback(ctx, jsonLogger, localSessionManagerDataSource)
                        )
                    /*if (databasePassphrase.isNotEmpty()) {
                        if (LOG_SECURE) Timber.tag(TAG).d("databasePassphrase isNotEmpty")
                        roomBuilder.openHelperFactory(
                            SupportFactory(
                                net.sqlcipher.database.SQLiteDatabase.getBytes(
                                    databasePassphrase.toCharArray()
                                )
                            )
                        ) //DATABASE_PASSPHRASE.toByteArray()
                    }*/
                    // Assign INSTANCE to the newly created database.
                    instance = roomBuilder.build()
                    if (LOG_DATABASE) Timber.tag(TAG).d("Room database built")
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }

        @Synchronized
        fun getTestInstance(ctx: Context, jsonLogger: Json? = null): JwSuiteDatabase {
            var instance = INSTANCE
            if (instance == null) {
                instance =
                    Room.inMemoryDatabaseBuilder(ctx, JwSuiteDatabase::class.java)
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
        fun getBackupInstance(
            context: Context, jsonLogger: Json? = Json,
            localSessionManagerDataSource: LocalSessionManagerDataSource
        ): JwSuiteDatabase {
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
                        .addCallback(
                            DatabaseCallback(context, jsonLogger, localSessionManagerDataSource)
                        )
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

        fun close() {
            synchronized(this) {
                INSTANCE?.close()
                INSTANCE = null
            }
        }
        /*fun export(fileName: String){
            val exportDir = File(Environment.getExternalStorageDirectory(), "")
            if (!exportDir.exists()) {
                exportDir.mkdirs()
            }

            val file = File(exportDir, fileName + ".csv")
            try {
                file.createNewFile()
                val csvWrite = CSVWriter(FileWriter(file))
                val curCSV: Cursor = db.query("SELECT * FROM $TableName", null)
                csvWrite.writeNext(curCSV.getColumnNames())
                while (curCSV.moveToNext()) {
                    //Which column you want to exprort
                    val arrStr = arrayOfNulls<String>(curCSV.getColumnCount())
                    for (i in 0 until curCSV.getColumnCount() - 1) arrStr[i] = curCSV.getString(i)
                    csvWrite.writeNext(arrStr)
                }
                csvWrite.close()
                curCSV.close()
                ToastHelper.showToast(this, "Exported", Toast.LENGTH_SHORT)
            } catch (sqlEx: Exception) {
                Log.e("MainActivity", sqlEx.message, sqlEx)
            }        }*/
    }

    /**
     * https://stackoverflow.com/questions/5955202/how-to-remove-database-from-emulator
     */
    class DatabaseCallback(
        private val ctx: Context, private val jsonLogger: Json? = null,
        private val localSessionManagerDataSource: LocalSessionManagerDataSource
    ) : Callback() {
        private val currentDateTime: OffsetDateTime = OffsetDateTime.now()

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            if (LOG_DATABASE) {
                Timber.tag(TAG).d("onCreate(...) called")
                Timber.tag(TAG)
                    .d("Database onCreate(...) called on thread '%s':", Thread.currentThread().name)
            }
            // https://developermemos.com/posts/prepopulate-android-room-data
            //CoroutineScope(Dispatchers.Main).launch {
            //GlobalScope.launch(Dispatchers.Main) {
            importJob = CoroutineScope(Dispatchers.IO).async {
                if (LOG_DATABASE) Timber.tag(TAG)
                    .d("onCreate -> CoroutineScope(Dispatchers.IO).async")
                val database = getInstance(ctx, jsonLogger, localSessionManagerDataSource)
                if (LOG_DATABASE) Timber.tag(TAG).d(
                    "onCreate -> Start thread '%s': database.getInstance(...)",
                    Thread.currentThread().name
                )
                //database.runInTransaction(Runnable {
                //if (LOG_DATABASE) Timber.tag(TAG).d("onCreate -> database.runInTransaction -> run()")
                //runBlocking {
                prePopulateDbWithDao(database)
                //}
                //})
                true
            }
            /*
            isImportExecute = true
            // moving to a new thread
            // Executors.newSingleThreadExecutor().execute{f}
            //GlobalScope.launch(Dispatchers.Main)
            //CoroutineScope(Dispatchers.IO).launch()
            isImportDone = CoroutineScope(Dispatchers.IO).async {
                Timber.tag(TAG)
                    .i("Start thread '%s': prePopulateDb(...)", Thread.currentThread().name)
                prePopulateDb(db)
                true
            }*/
            if (LOG_DATABASE) Timber.tag(TAG)
                .d("Database onCreate(...) ended on thread '%s':", Thread.currentThread().name)
        }

        // ================================= DAO =================================
        private suspend fun prePopulateDbWithDao(db: JwSuiteDatabase) {
            if (LOG_DATABASE) Timber.tag(TAG).d("prePopulateDbWithDao(...) called")
            // Default settings:
            insertDefAppSettings(db)
            // ==============================
            // GEO:
            // Default regions:
            val donRegion = insertDefRegion(db, GeoRegionEntity.donetskRegion(ctx))
            val lugRegion = insertDefRegion(db, GeoRegionEntity.luganskRegion(ctx))

            // DON districts:
            val maryinskyDistrict = insertDefRegionDistrict(
                db, GeoRegionDistrictEntity.maryinskyRegionDistrict(ctx, donRegion.regionId)
            )

            val donetskyDistrict = insertDefRegionDistrict(
                db, GeoRegionDistrictEntity.donetskyRegionDistrict(ctx, donRegion.regionId)
            )

            // DON localities:
            val donetsk = insertDeftLocality(
                db, GeoLocalityEntity.donetskLocality(ctx, donRegion.regionId)
            )
            val makeevka = insertDeftLocality(
                db, GeoLocalityEntity.makeevkaLocality(ctx, donRegion.regionId)
            )
            val marinka = insertDeftLocality(
                db, GeoLocalityEntity.marinkaLocality(
                    ctx, donRegion.regionId, maryinskyDistrict.regionDistrictId
                )
            )
            val mospino = insertDeftLocality(
                db, GeoLocalityEntity.mospinoLocality(
                    ctx, donRegion.regionId, donetskyDistrict.regionDistrictId
                )
            )
            // LUG localities:
            val lugansk = insertDeftLocality(
                db, GeoLocalityEntity.luganskLocality(ctx, lugRegion.regionId)
            )

            // Donetsk districts:
            val budyonovsky = insertDeftLocalityDistrict(
                db,
                GeoLocalityDistrictEntity.bdnLocalityDistrict(ctx, donetsk.localityId)
            )
            val kievsky = insertDeftLocalityDistrict(
                db,
                GeoLocalityDistrictEntity.kvkLocalityDistrict(ctx, donetsk.localityId)
            )
            val kuybyshevsky = insertDeftLocalityDistrict(
                db,
                GeoLocalityDistrictEntity.kbshLocalityDistrict(ctx, donetsk.localityId)
            )
            val voroshilovsky = insertDeftLocalityDistrict(
                db,
                GeoLocalityDistrictEntity.vrshLocalityDistrict(ctx, donetsk.localityId)
            )

            // Donetsk microdistricts:
            val donskoy = insertDefMicrodistrict(
                db,
                GeoMicrodistrictEntity.donskoyMicrodistrict(
                    ctx, budyonovsky.ldLocalitiesId, budyonovsky.localityDistrictId
                )
            )
            val cvetochny = insertDefMicrodistrict(
                db,
                GeoMicrodistrictEntity.cvetochnyMicrodistrict(
                    ctx, budyonovsky.ldLocalitiesId, budyonovsky.localityDistrictId
                )
            )

            // Donetsk streets:
            val strelkovojDivizii = insertDefStreet(
                db, GeoStreetEntity.strelkovojDiviziiStreet(ctx, donetsk.localityId)
            )
            insertDefStreetDistrict(db, strelkovojDivizii, budyonovsky, donskoy)
            insertDefStreetDistrict(db, strelkovojDivizii, budyonovsky, cvetochny)
            val nezavisimosti = insertDefStreet(
                db, GeoStreetEntity.nezavisimostiStreet(ctx, donetsk.localityId)
            )
            insertDefStreetDistrict(db, nezavisimosti, budyonovsky, donskoy)
            val baratynskogo = insertDefStreet(
                db, GeoStreetEntity.baratynskogoStreet(ctx, donetsk.localityId)
            )
            insertDefStreetDistrict(db, baratynskogo, budyonovsky)
            val patorgynskogo = insertDefStreet(
                db, GeoStreetEntity.patorgynskogoStreet(ctx, donetsk.localityId)
            )
            insertDefStreetDistrict(db, patorgynskogo, budyonovsky)
            val belogorodskaya = insertDefStreet(
                db, GeoStreetEntity.belogorodskayaStreet(ctx, donetsk.localityId)
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
                db, GeoStreetEntity.novomospinoStreet(ctx, mospino.localityId)
            )
            val gertsena =
                insertDefStreet(db, GeoStreetEntity.gertsenaStreet(ctx, mospino.localityId))


            // ==============================
            // CONGREGATION:
            // Default member roles:
            val adminRole = insertDefMemberRole(db, MemberRoleType.ADMIN)
            val userRole = insertDefMemberRole(db, MemberRoleType.USER)
            val territoriesRole = insertDefMemberRole(db, MemberRoleType.TERRITORIES)
            val billsRole = insertDefMemberRole(db, MemberRoleType.BILLS)
            val reportsRole = insertDefMemberRole(db, MemberRoleType.REPORTS)

            // Default congregations:
            val congregationDao = db.congregationDao()
            // 1
            val congregation1 = CongregationEntity.favoriteCongregation(
                ctx, donetsk.localityId
            )
            congregationDao.insert(congregation1)
            Timber.tag(TAG).i("CONGREGATION: Default 1 Congregation imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(congregation1)) }

            // Default Administrator:
            val adminMember =
                insertDefAdminMember(db, congregation1, listOf(adminRole, reportsRole))

            // 2
            val congregation2 = CongregationEntity.secondCongregation(
                ctx, donetsk.localityId
            )
            congregationDao.insert(congregation2)
            Timber.tag(TAG).i("CONGREGATION: Default 2 Congregation imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(congregation2)) }

            // Default Groups:
            val group11 = insertDefGroup(db, 1, congregation1)
            val group12 = insertDefGroup(db, 2, congregation1)
            val group13 = insertDefGroup(db, 3, congregation1)
            val group14 = insertDefGroup(db, 4, congregation1)
            val group15 = insertDefGroup(db, 5, congregation1)
            val group16 = insertDefGroup(db, 6, congregation1)
            val group17 = insertDefGroup(db, 7, congregation1)

            // Group members:
            val ivanov = insertDefMember(db, 1, congregation1, group11, listOf(adminRole, userRole))
            val petrov = insertDefMember(db, 2, congregation1, group11, listOf(userRole))
            val sidorov =
                insertDefMember(db, 1, congregation1, group12, listOf(userRole, territoriesRole))

            // ==============================
            // TERRITORY:
            // Categories:
            val houseTerritoryCategory = insertDefTerritoryCategory(
                db, TerritoryCategoryEntity.houseTerritoryCategory(ctx)
            )
            val floorTerritoryCategory = insertDefTerritoryCategory(
                db, TerritoryCategoryEntity.floorTerritoryCategory(ctx)
            )
            val roomTerritoryCategory = insertDefTerritoryCategory(
                db, TerritoryCategoryEntity.roomTerritoryCategory(ctx)
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
            // TRANSFERS:
            // Default transfer objects:
            val allTransferObject = insertDefTransferObject(db, TransferObjectType.ALL)
            val membersTransferObject = insertDefTransferObject(db, TransferObjectType.MEMBERS)
            val territoriesTransferObject =
                insertDefTransferObject(db, TransferObjectType.TERRITORIES)
            val billsTransferObject = insertDefTransferObject(db, TransferObjectType.BILLS)

            insertDefRoleTransferObject(db, adminRole, allTransferObject, false)
            insertDefRoleTransferObject(db, reportsRole, allTransferObject, false)
            insertDefRoleTransferObject(db, userRole, territoriesTransferObject, true)
            insertDefRoleTransferObject(db, territoriesRole, territoriesTransferObject, false)
            insertDefRoleTransferObject(db, billsRole, billsTransferObject, true)

            if (LOG_DATABASE) Timber.tag(TAG).d("prePopulateDbWithDao(...) successful ended")
        }

        // App Settings:
        private suspend fun insertDefAppSettings(db: JwSuiteDatabase) {
            val appSettingDao = db.appSettingDao()
            // Lang
            val lang = AppSettingEntity.langParam()
            appSettingDao.insert(lang)
            // Currency Code
            val currencyCode = AppSettingEntity.currencyCodeParam()
            appSettingDao.insert(currencyCode)
            // All Items
            val allItems = AppSettingEntity.allItemsParam(ctx)
            appSettingDao.insert(allItems)
            // Day Mu
            val dayMu = AppSettingEntity.dayMuParam(ctx)
            appSettingDao.insert(dayMu)
            // Month Mu
            val monthMu = AppSettingEntity.monthMuParam(ctx)
            appSettingDao.insert(monthMu)
            // Year Mu
            val yearMu = AppSettingEntity.yearMuParam(ctx)
            appSettingDao.insert(yearMu)
            // Person Num MU
            val personNumMu = AppSettingEntity.personNumMuParam(ctx)
            appSettingDao.insert(personNumMu)
            // Territory Business Mark
            val territoryBusinessMark = AppSettingEntity.territoryBusinessMarkParam(ctx)
            appSettingDao.insert(territoryBusinessMark)
            // Territory Processing Period
            val territoryProcessingPeriod = AppSettingEntity.territoryProcessingPeriodParam(ctx)
            appSettingDao.insert(territoryProcessingPeriod)
            // Territory At Hand Period
            val territoryAtHandPeriod = AppSettingEntity.territoryAtHandPeriodParam(ctx)
            appSettingDao.insert(territoryAtHandPeriod)
            // Territory Rooms Limit
            val territoryRoomsLimit = AppSettingEntity.territoryRoomsLimitParam(ctx)
            appSettingDao.insert(territoryRoomsLimit)
            // Territory Max Rooms
            val territoryMaxRoomsParam = AppSettingEntity.territoryMaxRoomsParam(ctx)
            appSettingDao.insert(territoryMaxRoomsParam)
            // Territory Idle Period
            val territoryIdlePeriod = AppSettingEntity.territoryIdlePeriodParam(ctx)
            appSettingDao.insert(territoryIdlePeriod)
            Timber.tag(TAG).i("Default app parameters imported")
            jsonLogger?.let {
                Timber.tag(TAG)
                    .i(
                        ": {\"params\": {\"lang\": {%s}, \"currencyCode\": {%s}, \"allItems\": {%s}, \"dayMu\": {%s}, \"monthMu\": {%s}, \"yearMu\": {%s}, \"personNumMu\": {%s}, \"territoryBusinessMark\": {%s}, \"territoryProcessingPeriod\": {%s}, \"territoryAtHandPeriod\": {%s}, \"territoryRoomsLimit\": {%s}, \"territoryMaxRoomsParam\": {%s}, \"territoryIdlePeriod\": {%s}}",
                        it.encodeToString(lang),
                        it.encodeToString(currencyCode),
                        it.encodeToString(allItems),
                        it.encodeToString(dayMu),
                        it.encodeToString(monthMu),
                        it.encodeToString(yearMu),
                        it.encodeToString(personNumMu),
                        it.encodeToString(territoryBusinessMark),
                        it.encodeToString(territoryProcessingPeriod),
                        it.encodeToString(territoryAtHandPeriod),
                        it.encodeToString(territoryRoomsLimit),
                        it.encodeToString(territoryMaxRoomsParam),
                        it.encodeToString(territoryIdlePeriod)
                    )
            }
        }

        // GEO:
        private suspend fun insertDefRegion(db: JwSuiteDatabase, region: GeoRegionEntity):
                GeoRegionEntity {
            val regionDao = db.geoRegionDao()
            val textContent =
                GeoRegionTlEntity.regionTl(ctx, region.regionCode, region.regionId)
            regionDao.insert(region, textContent)
            Timber.tag(TAG).i("GEO: Default region imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"region\": {%s}, \"tl\": {%s}}",
                    it.encodeToString(region),
                    it.encodeToString(textContent)
                )
            }
            return region
        }

        private suspend fun insertDefRegionDistrict(
            db: JwSuiteDatabase, regionDistrict: GeoRegionDistrictEntity
        ): GeoRegionDistrictEntity {
            val regionDistrictDao = db.geoRegionDistrictDao()
            val textContent =
                GeoRegionDistrictTlEntity.regionDistrictTl(
                    ctx,
                    regionDistrict.regDistrictShortName, regionDistrict.regionDistrictId
                )
            regionDistrictDao.insert(regionDistrict, textContent)
            Timber.tag(TAG).i("GEO: Default region District imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"regionDistrict\": {%s}, \"tl\": {%s}}",
                    it.encodeToString(regionDistrict), it.encodeToString(textContent)
                )
            }
            return regionDistrict
        }

        private suspend fun insertDeftLocality(db: JwSuiteDatabase, locality: GeoLocalityEntity):
                GeoLocalityEntity {
            val localityDao = db.geoLocalityDao()
            val textContent =
                GeoLocalityTlEntity.localityTl(ctx, locality.localityCode, locality.localityId)
            localityDao.insert(locality, textContent)
            Timber.tag(TAG).i("GEO: Default locality imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"locality\": {%s}, \"tl\": {%s}}",
                    it.encodeToString(locality), it.encodeToString(textContent)
                )
            }
            return locality
        }

        private suspend fun insertDeftLocalityDistrict(
            db: JwSuiteDatabase, localityDistrict: GeoLocalityDistrictEntity
        ): GeoLocalityDistrictEntity {
            val localityDistrictDao = db.geoLocalityDistrictDao()
            val textContent =
                GeoLocalityDistrictTlEntity.localityDistrictTl(
                    ctx,
                    localityDistrict.locDistrictShortName, localityDistrict.localityDistrictId
                )
            localityDistrictDao.insert(localityDistrict, textContent)
            Timber.tag(TAG).i("GEO: Default locality district imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"localityDistrict\": {%s}, \"tl\": {%s}}",
                    it.encodeToString(localityDistrict), it.encodeToString(textContent)
                )
            }
            return localityDistrict
        }

        private suspend fun insertDefMicrodistrict(
            db: JwSuiteDatabase, microdistrict: GeoMicrodistrictEntity
        ): GeoMicrodistrictEntity {
            val microdistrictDao = db.geoMicrodistrictDao()
            val textContent = GeoMicrodistrictTlEntity.microdistrictTl(
                ctx,
                microdistrict.microdistrictShortName, microdistrict.microdistrictId
            )
            microdistrictDao.insert(microdistrict, textContent)
            Timber.tag(TAG).i("GEO: Default locality microdistrict imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"microdistrict\": {%s}, \"tl\": {%s}}",
                    it.encodeToString(microdistrict), it.encodeToString(textContent)
                )
            }
            return microdistrict
        }

        private suspend fun insertDefStreet(db: JwSuiteDatabase, street: GeoStreetEntity):
                GeoStreetEntity {
            val streetDao = db.geoStreetDao()
            val textContent = GeoStreetTlEntity.streetTl(
                ctx, street.streetHashCode, street.streetId
            )
            streetDao.insert(street, textContent)
            Timber.tag(TAG).i("GEO: Default street imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"street\": {%s}, \"tl\": {%s}}",
                    it.encodeToString(street), it.encodeToString(textContent)
                )
            }
            return street
        }

        private suspend fun insertDefStreetDistrict(
            db: JwSuiteDatabase, street: GeoStreetEntity,
            localityDistrict: GeoLocalityDistrictEntity,
            microdistrict: GeoMicrodistrictEntity? = null
        ) {
            val streetDao = db.geoStreetDao()
            val streetDistrict = GeoStreetDistrictEntity.defaultDistrictStreet(
                streetId = street.streetId,
                localityDistrictId = localityDistrict.localityDistrictId,
                microdistrictId = microdistrict?.microdistrictId
            )
            streetDao.insert(streetDistrict)
            Timber.tag(TAG).i("GEO: Default street district imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(streetDistrict)) }
        }

        private suspend fun insertDefHouse(db: JwSuiteDatabase, house: HouseEntity): HouseEntity {
            val houseDao = db.houseDao()
            houseDao.insert(house)
            Timber.tag(TAG).i("GEO: Default house imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(house)) }
            return house
        }

        // CONGREGATION:
        private suspend fun insertDefMemberRole(db: JwSuiteDatabase, roleType: MemberRoleType):
                RoleEntity {
            val memberDao = db.memberDao()
            val role = when (roleType) {
                MemberRoleType.ADMIN -> RoleEntity.adminRole(ctx)
                MemberRoleType.USER -> RoleEntity.userRole(ctx)
                MemberRoleType.TERRITORIES -> RoleEntity.territoriesRole(ctx)
                MemberRoleType.BILLS -> RoleEntity.billsRole(ctx)
                MemberRoleType.REPORTS -> RoleEntity.reportsRole(ctx)
            }
            memberDao.insert(role)
            Timber.tag(TAG).i("Default role imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(": {\"role\": {%s}}", it.encodeToString(role))
            }
            return role
        }

        private suspend fun insertDefGroup(
            db: JwSuiteDatabase, groupNum: Int, congregation: CongregationEntity
        ): GroupEntity {
            val groupDao = db.groupDao()
            val group = when (groupNum) {
                1 -> GroupEntity.group1(ctx, congregation.congregationId)
                2 -> GroupEntity.group2(ctx, congregation.congregationId)
                3 -> GroupEntity.group3(ctx, congregation.congregationId)
                4 -> GroupEntity.group4(ctx, congregation.congregationId)
                5 -> GroupEntity.group5(ctx, congregation.congregationId)
                6 -> GroupEntity.group6(ctx, congregation.congregationId)
                7 -> GroupEntity.group7(ctx, congregation.congregationId)
                8 -> GroupEntity.group8(ctx, congregation.congregationId)
                9 -> GroupEntity.group9(ctx, congregation.congregationId)
                10 -> GroupEntity.group10(ctx, congregation.congregationId)
                11 -> GroupEntity.group11(ctx, congregation.congregationId)
                12 -> GroupEntity.group12(ctx, congregation.congregationId)
                else -> null
            }
            group?.let {
                groupDao.insert(it)
                Timber.tag(TAG).i("CONGREGATION: Default group imported")
                jsonLogger?.let { logger ->
                    Timber.tag(TAG).i(": {\"group\": {%s}}", logger.encodeToString(it))
                }
            }
            return group!!
        }

        private suspend fun insertDefMember(
            db: JwSuiteDatabase,
            memberNumInGroup: Int,
            congregation: CongregationEntity,
            group: GroupEntity,
            roles: List<RoleEntity> = emptyList()
        ): MemberEntity {
            val memberDao = db.memberDao()
            val member = when (congregation.congregationNum) {
                "1" -> when (group.groupNum) {
                    1 -> when (memberNumInGroup) {
                        1 -> MemberEntity.ivanovMember11(ctx, group.groupId)
                        2 -> MemberEntity.petrovMember12(ctx, group.groupId)
                        else -> null
                    }

                    2 -> when (memberNumInGroup) {
                        1 -> MemberEntity.sidorovMember21(ctx, group.groupId)
                        else -> null
                    }

                    else -> null
                }

                "2" -> when (group.groupNum) {
                    1 -> when (memberNumInGroup) {
                        1 -> MemberEntity.tarasovaMember11(ctx, group.groupId)
                        2 -> MemberEntity.shevchukMember12(ctx, group.groupId)
                        else -> null
                    }

                    2 -> when (memberNumInGroup) {
                        1 -> MemberEntity.matveychukMember21(ctx, group.groupId)
                        else -> null
                    }

                    else -> null
                }

                else -> null
            }
            member?.let { member ->
                memberDao.insert(member)
                val memberCongregation = MemberCongregationCrossRefEntity.defaultCongregationMember(
                    congregationId = congregation.congregationId, memberId = member.memberId
                )
                memberDao.insert(memberCongregation)
                val memberMovement = MemberMovementEntity.defaultMemberMovement(
                    memberId = member.memberId, memberType = MemberType.PREACHER
                )
                memberDao.insert(memberMovement)
                Timber.tag(TAG).i("CONGREGATION: Default member imported")
                jsonLogger?.let { logger ->
                    Timber.tag(TAG).i(
                        ": {\"member\": {%s}, \"memberCongregation\": {%s}, \"memberMovement\": {%s}, \"memberRoles\": [",
                        logger.encodeToString(member),
                        logger.encodeToString(memberCongregation),
                        logger.encodeToString(memberMovement)
                    )
                }
                roles.forEach { role ->
                    val memberRole = MemberRoleEntity.defaultMemberRole(
                        memberId = member.memberId, roleId = role.roleId
                    )
                    memberDao.insert(memberRole)
                    jsonLogger?.let { logger ->
                        Timber.tag(TAG).i("{%s},", logger.encodeToString(memberRole))
                    }
                }
                Timber.tag(TAG).i("]}")
            }
            return member!!
        }

        private suspend fun insertDefAdminMember(
            db: JwSuiteDatabase,
            congregation: CongregationEntity,
            roles: List<RoleEntity> = emptyList()
        ): MemberEntity {
            val memberDao = db.memberDao()
            val member = MemberEntity.adminMember(ctx)
            memberDao.insert(member)
            val memberCongregation = MemberCongregationCrossRefEntity.defaultCongregationMember(
                congregationId = congregation.congregationId, memberId = member.memberId
            )
            memberDao.insert(memberCongregation)
            val memberMovement = MemberMovementEntity.defaultMemberMovement(
                memberId = member.memberId, memberType = MemberType.SERVICE
            )
            memberDao.insert(memberMovement)
            Timber.tag(TAG).i("CONGREGATION: Default Administrator imported")
            jsonLogger?.let { logger ->
                Timber.tag(TAG).i(
                    ": {\"member\": {%s}, \"memberCongregation\": {%s}, \"memberMovement\": {%s}, \"memberRoles\": [",
                    logger.encodeToString(member),
                    logger.encodeToString(memberCongregation),
                    logger.encodeToString(memberMovement)
                )
            }
            roles.forEach {
                val memberRole = MemberRoleEntity.defaultMemberRole(
                    memberId = member.memberId, roleId = it.roleId
                )
                memberDao.insert(memberRole)
                jsonLogger?.let { logger ->
                    Timber.tag(TAG).i("{%s},", logger.encodeToString(memberRole))
                }
            }
            Timber.tag(TAG).i("]}")
            return member
        }

        // TERRITORIES:
        private suspend fun insertDefTerritoryCategory(
            db: JwSuiteDatabase, territoryCategory: TerritoryCategoryEntity
        ): TerritoryCategoryEntity {
            val territoryCategoryDao = db.territoryCategoryDao()
            territoryCategoryDao.insert(territoryCategory)
            Timber.tag(TAG).i("TERRITORY: Default territory category imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(territoryCategory)) }
            return territoryCategory
        }

        private suspend fun insertDefTerritories(
            db: JwSuiteDatabase, territoryCategory: TerritoryCategoryEntity,
            congregation: CongregationEntity,
            locality: GeoLocalityEntity, localityDistrict: GeoLocalityDistrictEntity,
            microdistrict: GeoMicrodistrictEntity
        ): MutableList<TerritoryEntity> {
            val territoryDao = db.territoryDao()
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
                    //isInPerimeter = num % 10 == 0,
                    isGroupMinistry = num % 20 == 0,
                    territoryNum = num
                )
                territoryDao.insert(territory)
                val congregationTerritory =
                    CongregationTerritoryCrossRefEntity.defaultCongregationTerritory(
                        congregationId = congregation.congregationId,
                        territoryId = territory.territoryId
                    )
                territoryDao.insert(congregationTerritory)
                territories.add(territory)
            }
            Timber.tag(TAG).i("TERRITORIES: Default territories imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"territories\": {\"count\": %s, \"congregation\": {%s}}}",
                    territories.size, it.encodeToString(congregation)
                )
            }
            return territories
        }

        private suspend fun insertDefTerritoryMember(
            db: JwSuiteDatabase, territory: TerritoryEntity, member: MemberEntity
        ): TerritoryMemberCrossRefEntity {
            val territoryDao = db.territoryDao()
            val territoryMember = TerritoryMemberCrossRefEntity.defaultTerritoryMember(
                territoryId = territory.territoryId, memberId = member.memberId,
            )
            territoryDao.insert(territoryMember)
            val handOutTerritory = territory.copy(isProcessed = false)
            territoryDao.update(handOutTerritory)
            Timber.tag(TAG).i("TERRITORY: Default territory member imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(territoryMember)) }
            Timber.tag(TAG).i("TERRITORY: Default hand out territory imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(handOutTerritory)) }
            return territoryMember
        }

        private suspend fun deliveryDefTerritoryMember(
            db: JwSuiteDatabase, territory: TerritoryEntity,
            territoryMember: TerritoryMemberCrossRefEntity
        ) {
            val territoryDao = db.territoryDao()
            val deliveryTerritoryMember = territoryMember.copy(deliveryDate = OffsetDateTime.now())
            territoryDao.update(deliveryTerritoryMember)
            val idleTerritory = territory.copy(isProcessed = true)
            territoryDao.update(idleTerritory)
            Timber.tag(TAG).i("TERRITORY: Delivery imported territory member")
            jsonLogger?.let {
                Timber.tag(TAG).i(": {%s}", it.encodeToString(deliveryTerritoryMember))
            }
            Timber.tag(TAG).i("TERRITORY: Delivery imported territory")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(idleTerritory)) }
        }

        private suspend fun insertDefTerritoryStreet(
            db: JwSuiteDatabase, territory: TerritoryEntity, street: GeoStreetEntity,
            isEven: Boolean? = null, isPrivateSector: Boolean? = null
        ) {
            val territoryDao = db.territoryStreetDao()
            val territoryStreet = TerritoryStreetEntity.privateSectorTerritoryStreet(
                territoryId = territory.territoryId, streetId = street.streetId, isEven = isEven
            )
            territoryDao.insert(territoryStreet)
            Timber.tag(TAG).i("TERRITORY: Default territory street imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(territoryStreet)) }
        }

        // TRANSFERS:
        private suspend fun insertDefTransferObject(
            db: JwSuiteDatabase, transferObjectType: TransferObjectType
        ): TransferObjectEntity {
            val transferDao = db.transferDao()
            val transferObject = when (transferObjectType) {
                TransferObjectType.ALL -> TransferObjectEntity.allTransferObject(ctx)
                TransferObjectType.MEMBERS -> TransferObjectEntity.membersTransferObject(ctx)
                TransferObjectType.TERRITORIES -> TransferObjectEntity.territoriesTransferObject(ctx)
                TransferObjectType.BILLS -> TransferObjectEntity.billsTransferObject(ctx)
            }
            transferDao.insert(transferObject)
            Timber.tag(TAG).i("Default transfer object imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(": {\"transferObject\": {%s}}", it.encodeToString(transferObject))
            }
            return transferObject
        }

        private suspend fun insertDefRoleTransferObject(
            db: JwSuiteDatabase, role: RoleEntity, transferObject: TransferObjectEntity,
            isPersonalData: Boolean
        ): RoleTransferObjectEntity {
            val transferDao = db.transferDao()
            val roleTransferObject = RoleTransferObjectEntity.defaultRoleTransferObject(
                roleId = role.roleId, transferObjectId = transferObject.transferObjectId,
                isPersonalData = isPersonalData
            )
            transferDao.insert(roleTransferObject)
            Timber.tag(TAG).i("TRANSFERS: Default Role Transfer Object imported")
            jsonLogger?.let { logger ->
                Timber.tag(TAG).i(
                    ": {\"roleTransferObject\": {%s}}", logger.encodeToString(roleTransferObject)
                )
            }
            return roleTransferObject
        }

        // ================================= SupportSQLiteDatabase =================================
        private fun prePopulateDb(db: SupportSQLiteDatabase) {
            Timber.tag(TAG).i("prePopulateDb(...) called")
            db.beginTransaction()
            try {
                // Default settings:
                insertDefAppSettings(db)
                // ==============================
                // GEO:
                // Default regions:
                val donRegion = insertDefRegion(db, GeoRegionEntity.donetskRegion(ctx))
                val lugRegion = insertDefRegion(db, GeoRegionEntity.luganskRegion(ctx))

                // DON districts:
                val maryinskyDistrict = insertDefRegionDistrict(
                    db, GeoRegionDistrictEntity.maryinskyRegionDistrict(ctx, donRegion.regionId)
                )

                val donetskyDistrict = insertDefRegionDistrict(
                    db, GeoRegionDistrictEntity.donetskyRegionDistrict(ctx, donRegion.regionId)
                )

                // DON localities:
                val donetsk = insertDeftLocality(
                    db, GeoLocalityEntity.donetskLocality(ctx, donRegion.regionId)
                )
                val makeevka = insertDeftLocality(
                    db, GeoLocalityEntity.makeevkaLocality(ctx, donRegion.regionId)
                )
                val marinka = insertDeftLocality(
                    db, GeoLocalityEntity.marinkaLocality(
                        ctx, donRegion.regionId, maryinskyDistrict.regionDistrictId
                    )
                )
                val mospino = insertDeftLocality(
                    db, GeoLocalityEntity.mospinoLocality(
                        ctx, donRegion.regionId, donetskyDistrict.regionDistrictId
                    )
                )
                // LUG localities:
                val lugansk = insertDeftLocality(
                    db, GeoLocalityEntity.luganskLocality(ctx, lugRegion.regionId)
                )

                // Donetsk districts:
                val budyonovsky = insertDeftLocalityDistrict(
                    db,
                    GeoLocalityDistrictEntity.bdnLocalityDistrict(ctx, donetsk.localityId)
                )
                val kievsky = insertDeftLocalityDistrict(
                    db,
                    GeoLocalityDistrictEntity.kvkLocalityDistrict(ctx, donetsk.localityId)
                )
                val kuybyshevsky = insertDeftLocalityDistrict(
                    db,
                    GeoLocalityDistrictEntity.kbshLocalityDistrict(ctx, donetsk.localityId)
                )
                val voroshilovsky = insertDeftLocalityDistrict(
                    db,
                    GeoLocalityDistrictEntity.vrshLocalityDistrict(ctx, donetsk.localityId)
                )

                // Donetsk microdistricts:
                val donskoy = insertDefMicrodistrict(
                    db,
                    GeoMicrodistrictEntity.donskoyMicrodistrict(
                        ctx, budyonovsky.ldLocalitiesId, budyonovsky.localityDistrictId
                    )
                )
                val cvetochny = insertDefMicrodistrict(
                    db,
                    GeoMicrodistrictEntity.cvetochnyMicrodistrict(
                        ctx, budyonovsky.ldLocalitiesId, budyonovsky.localityDistrictId
                    )
                )

                // Donetsk streets:
                val strelkovojDivizii = insertDefStreet(
                    db, GeoStreetEntity.strelkovojDiviziiStreet(ctx, donetsk.localityId)
                )
                insertDefStreetDistrict(db, strelkovojDivizii, budyonovsky, donskoy)
                insertDefStreetDistrict(db, strelkovojDivizii, budyonovsky, cvetochny)
                val nezavisimosti = insertDefStreet(
                    db, GeoStreetEntity.nezavisimostiStreet(ctx, donetsk.localityId)
                )
                insertDefStreetDistrict(db, nezavisimosti, budyonovsky, donskoy)
                val baratynskogo = insertDefStreet(
                    db, GeoStreetEntity.baratynskogoStreet(ctx, donetsk.localityId)
                )
                insertDefStreetDistrict(db, baratynskogo, budyonovsky)
                val patorgynskogo = insertDefStreet(
                    db, GeoStreetEntity.patorgynskogoStreet(ctx, donetsk.localityId)
                )
                insertDefStreetDistrict(db, patorgynskogo, budyonovsky)
                val belogorodskaya = insertDefStreet(
                    db, GeoStreetEntity.belogorodskayaStreet(ctx, donetsk.localityId)
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
                    db, GeoStreetEntity.novomospinoStreet(ctx, mospino.localityId)
                )
                val gertsena =
                    insertDefStreet(db, GeoStreetEntity.gertsenaStreet(ctx, mospino.localityId))


                // ==============================
                // CONGREGATION:
                // Default member roles:
                val adminRole = insertDefMemberRole(db, MemberRoleType.ADMIN)
                val userRole = insertDefMemberRole(db, MemberRoleType.USER)
                val territoriesRole = insertDefMemberRole(db, MemberRoleType.TERRITORIES)
                val billsRole = insertDefMemberRole(db, MemberRoleType.BILLS)
                val reportsRole = insertDefMemberRole(db, MemberRoleType.REPORTS)

                // Default Administrator:
                val adminMember = insertDefAdminMember(db, adminRole)

                // Default congregations:
                // 1
                val congregation1 = CongregationEntity.favoriteCongregation(
                    ctx, donetsk.localityId
                )
                db.insert(
                    CongregationEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(congregation1)
                )
                Timber.tag(TAG).i("CONGREGATION: Default 1 Congregation imported")
                jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(congregation1)) }
                // 2
                val congregation2 = CongregationEntity.secondCongregation(
                    ctx, donetsk.localityId
                )
                db.insert(
                    CongregationEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(congregation2)
                )
                Timber.tag(TAG).i("CONGREGATION: Default 2 Congregation imported")
                jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(congregation2)) }

                // Default Groups:
                val group11 = insertDefGroup(db, 1, congregation1)
                val group12 = insertDefGroup(db, 2, congregation1)
                val group13 = insertDefGroup(db, 3, congregation1)
                val group14 = insertDefGroup(db, 4, congregation1)
                val group15 = insertDefGroup(db, 5, congregation1)
                val group16 = insertDefGroup(db, 6, congregation1)
                val group17 = insertDefGroup(db, 7, congregation1)

                // Group members:
                val ivanov = insertDefMember(db, 1, congregation1, group11, adminRole)
                val petrov = insertDefMember(db, 2, congregation1, group11, userRole)
                val sidorov = insertDefMember(db, 1, congregation1, group12, territoriesRole)

                // ==============================
                // TERRITORY:
                // Categories:
                val houseTerritoryCategory = insertDefTerritoryCategory(
                    db, TerritoryCategoryEntity.houseTerritoryCategory(ctx)
                )
                val floorTerritoryCategory = insertDefTerritoryCategory(
                    db, TerritoryCategoryEntity.floorTerritoryCategory(ctx)
                )
                val roomTerritoryCategory = insertDefTerritoryCategory(
                    db, TerritoryCategoryEntity.roomTerritoryCategory(ctx)
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
                // TRANSFERS:
                // Default transfer objects:
                val allTransferObject = insertDefTransferObject(db, TransferObjectType.ALL)
                val membersTransferObject = insertDefTransferObject(db, TransferObjectType.MEMBERS)
                val territoriesTransferObject =
                    insertDefTransferObject(db, TransferObjectType.TERRITORIES)
                val billsTransferObject = insertDefTransferObject(db, TransferObjectType.BILLS)

                insertDefRoleTransferObject(db, adminRole, allTransferObject, false)
                insertDefRoleTransferObject(db, reportsRole, allTransferObject, false)
                insertDefRoleTransferObject(db, userRole, territoriesTransferObject, true)
                insertDefRoleTransferObject(db, territoriesRole, territoriesTransferObject, false)
                insertDefRoleTransferObject(db, billsRole, billsTransferObject, true)

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
            val allItems = AppSettingEntity.allItemsParam(ctx)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(allItems)
            )
            // Day Mu
            val dayMu = AppSettingEntity.dayMuParam(ctx)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(dayMu)
            )
            // Month Mu
            val monthMu = AppSettingEntity.monthMuParam(ctx)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(monthMu)
            )
            // Year Mu
            val yearMu = AppSettingEntity.yearMuParam(ctx)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(monthMu)
            )
            // Person Num MU
            val personNumMu = AppSettingEntity.personNumMuParam(ctx)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(personNumMu)
            )
            // Territory Business Mark
            val territoryBusinessMark = AppSettingEntity.territoryBusinessMarkParam(ctx)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryBusinessMark)
            )
            // Territory Processing Period
            val territoryProcessingPeriod = AppSettingEntity.territoryProcessingPeriodParam(ctx)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryProcessingPeriod)
            )
            // Territory At Hand Period
            val territoryAtHandPeriod = AppSettingEntity.territoryAtHandPeriodParam(ctx)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryAtHandPeriod)
            )
            // Territory Rooms Limit
            val territoryRoomsLimit = AppSettingEntity.territoryRoomsLimitParam(ctx)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryRoomsLimit)
            )
            // Territory Max Rooms
            val territoryMaxRoomsParam = AppSettingEntity.territoryMaxRoomsParam(ctx)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryMaxRoomsParam)
            )
            // Territory Idle Period
            val territoryIdlePeriod = AppSettingEntity.territoryIdlePeriodParam(ctx)
            db.insert(
                AppSettingEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryIdlePeriod)
            )
            Timber.tag(TAG).i("Default app parameters imported")
            jsonLogger?.let {
                Timber.tag(TAG)
                    .i(
                        ": {\"params\": {\"lang\": {%s}, \"currencyCode\": {%s}, \"allItems\": {%s}, \"dayMu\": {%s}, \"monthMu\": {%s}, \"yearMu\": {%s}, \"personNumMu\": {%s}, \"territoryBusinessMark\": {%s}, \"territoryProcessingPeriod\": {%s}, \"territoryAtHandPeriod\": {%s}, \"territoryRoomsLimit\": {%s}, \"territoryMaxRoomsParam\": {%s}, \"territoryIdlePeriod\": {%s}}",
                        it.encodeToString(lang),
                        it.encodeToString(currencyCode),
                        it.encodeToString(allItems),
                        it.encodeToString(dayMu),
                        it.encodeToString(monthMu),
                        it.encodeToString(yearMu),
                        it.encodeToString(personNumMu),
                        it.encodeToString(territoryBusinessMark),
                        it.encodeToString(territoryProcessingPeriod),
                        it.encodeToString(territoryAtHandPeriod),
                        it.encodeToString(territoryRoomsLimit),
                        it.encodeToString(territoryMaxRoomsParam),
                        it.encodeToString(territoryIdlePeriod)
                    )
            }
        }

        // GEO:
        private fun insertDefRegion(db: SupportSQLiteDatabase, region: GeoRegionEntity):
                GeoRegionEntity {
            val textContent =
                GeoRegionTlEntity.regionTl(ctx, region.regionCode, region.regionId)
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
                    ": {\"region\": {%s}, \"tl\": {%s}}",
                    it.encodeToString(region),
                    it.encodeToString(textContent)
                )
            }
            return region
        }

        private fun insertDefRegionDistrict(
            db: SupportSQLiteDatabase, regionDistrict: GeoRegionDistrictEntity
        ): GeoRegionDistrictEntity {
            val textContent =
                GeoRegionDistrictTlEntity.regionDistrictTl(
                    ctx,
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
                    it.encodeToString(regionDistrict), it.encodeToString(textContent)
                )
            }
            return regionDistrict
        }

        private fun insertDeftLocality(db: SupportSQLiteDatabase, locality: GeoLocalityEntity):
                GeoLocalityEntity {
            val textContent =
                GeoLocalityTlEntity.localityTl(ctx, locality.localityCode, locality.localityId)
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
                    it.encodeToString(locality), it.encodeToString(textContent)
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
                    ctx,
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
                    it.encodeToString(localityDistrict), it.encodeToString(textContent)
                )
            }
            return localityDistrict
        }

        private fun insertDefMicrodistrict(
            db: SupportSQLiteDatabase, microdistrict: GeoMicrodistrictEntity
        ): GeoMicrodistrictEntity {
            val textContent = GeoMicrodistrictTlEntity.microdistrictTl(
                ctx,
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
                    it.encodeToString(microdistrict), it.encodeToString(textContent)
                )
            }
            return microdistrict
        }

        private fun insertDefStreet(db: SupportSQLiteDatabase, street: GeoStreetEntity):
                GeoStreetEntity {
            val textContent = GeoStreetTlEntity.streetTl(
                ctx, street.streetHashCode, street.streetId
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
                    it.encodeToString(street), it.encodeToString(textContent)
                )
            }
            return street
        }

        private fun insertDefStreetDistrict(
            db: SupportSQLiteDatabase, street: GeoStreetEntity,
            localityDistrict: GeoLocalityDistrictEntity,
            microdistrict: GeoMicrodistrictEntity? = null
        ) {
            val streetDistrict = GeoStreetDistrictEntity.defaultDistrictStreet(
                streetId = street.streetId,
                localityDistrictId = localityDistrict.localityDistrictId,
                microdistrictId = microdistrict?.microdistrictId
            )
            db.insert(
                GeoStreetDistrictEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(streetDistrict)
            )
            Timber.tag(TAG).i("GEO: Default street district imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(streetDistrict)) }
        }

        private fun insertDefHouse(db: SupportSQLiteDatabase, house: HouseEntity): HouseEntity {
            db.insert(
                HouseEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(house)
            )
            Timber.tag(TAG).i("GEO: Default house imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(house)) }
            return house
        }

        // CONGREGATION:
        private fun insertDefMemberRole(db: SupportSQLiteDatabase, roleType: MemberRoleType):
                RoleEntity {
            val role = when (roleType) {
                MemberRoleType.ADMIN -> RoleEntity.adminRole(ctx)
                MemberRoleType.USER -> RoleEntity.userRole(ctx)
                MemberRoleType.TERRITORIES -> RoleEntity.territoriesRole(ctx)
                MemberRoleType.BILLS -> RoleEntity.billsRole(ctx)
                MemberRoleType.REPORTS -> RoleEntity.reportsRole(ctx)
            }
            db.insert(
                RoleEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(role)
            )
            Timber.tag(TAG).i("Default member role imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(": {\"role\": {%s}}", it.encodeToString(role))
            }
            return role
        }

        private fun insertDefGroup(
            db: SupportSQLiteDatabase, groupNum: Int, congregation: CongregationEntity
        ): GroupEntity {
            val group = when (groupNum) {
                1 -> GroupEntity.group1(ctx, congregation.congregationId)
                2 -> GroupEntity.group2(ctx, congregation.congregationId)
                3 -> GroupEntity.group3(ctx, congregation.congregationId)
                4 -> GroupEntity.group4(ctx, congregation.congregationId)
                5 -> GroupEntity.group5(ctx, congregation.congregationId)
                6 -> GroupEntity.group6(ctx, congregation.congregationId)
                7 -> GroupEntity.group7(ctx, congregation.congregationId)
                8 -> GroupEntity.group8(ctx, congregation.congregationId)
                9 -> GroupEntity.group9(ctx, congregation.congregationId)
                10 -> GroupEntity.group10(ctx, congregation.congregationId)
                11 -> GroupEntity.group11(ctx, congregation.congregationId)
                12 -> GroupEntity.group12(ctx, congregation.congregationId)
                else -> null
            }
            group?.let {
                db.insert(
                    GroupEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(it)
                )
                Timber.tag(TAG).i("CONGREGATION: Default group imported")
                jsonLogger?.let { logger ->
                    Timber.tag(TAG).i(": {\"group\": {%s}}", logger.encodeToString(it))
                }
            }
            return group!!
        }

        private fun insertDefMember(
            db: SupportSQLiteDatabase, memberNumInGroup: Int,
            congregation: CongregationEntity, group: GroupEntity, role: RoleEntity
        ): MemberEntity {
            val member = when (congregation.congregationNum) {
                "1" -> when (group.groupNum) {
                    1 -> when (memberNumInGroup) {
                        1 -> MemberEntity.ivanovMember11(ctx, group.groupId)
                        2 -> MemberEntity.petrovMember12(ctx, group.groupId)
                        else -> null
                    }

                    2 -> when (memberNumInGroup) {
                        1 -> MemberEntity.sidorovMember21(ctx, group.groupId)
                        else -> null
                    }

                    else -> null
                }

                "2" -> when (group.groupNum) {
                    1 -> when (memberNumInGroup) {
                        1 -> MemberEntity.tarasovaMember11(ctx, group.groupId)
                        2 -> MemberEntity.shevchukMember12(ctx, group.groupId)
                        else -> null
                    }

                    2 -> when (memberNumInGroup) {
                        1 -> MemberEntity.matveychukMember21(ctx, group.groupId)
                        else -> null
                    }

                    else -> null
                }

                else -> null
            }
            member?.let {
                db.insert(
                    MemberEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(it)
                )
                val memberCongregation = MemberCongregationCrossRefEntity.defaultCongregationMember(
                    congregationId = congregation.congregationId, memberId = it.memberId
                )
                db.insert(
                    MemberCongregationCrossRefEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(memberCongregation)
                )
                val memberMovement = MemberMovementEntity.defaultMemberMovement(
                    memberId = it.memberId, memberType = MemberType.PREACHER
                )
                db.insert(
                    MemberMovementEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(memberMovement)
                )
                val memberRole = MemberRoleEntity.defaultMemberRole(
                    memberId = it.memberId, roleId = role.roleId
                )
                db.insert(
                    MemberRoleEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                    Mapper.toContentValues(memberRole)
                )
                Timber.tag(TAG).i("CONGREGATION: Default member imported")
                jsonLogger?.let { logger ->
                    Timber.tag(TAG).i(
                        ": {\"member\": {%s}, \"memberCongregation\": {%s}, \"memberMovement\": {%s}, \"memberRole\": {%s}}",
                        logger.encodeToString(it),
                        logger.encodeToString(memberCongregation),
                        logger.encodeToString(memberMovement),
                        logger.encodeToString(memberRole)
                    )
                }
            }
            return member!!
        }

        private fun insertDefAdminMember(
            db: SupportSQLiteDatabase, role: RoleEntity
        ): MemberEntity {
            val member = MemberEntity.adminMember(ctx)
            db.insert(
                MemberEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(member)
            )
            val memberMovement = MemberMovementEntity.defaultMemberMovement(
                memberId = member.memberId, memberType = MemberType.SERVICE
            )
            db.insert(
                MemberMovementEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(memberMovement)
            )
            val memberRole = MemberRoleEntity.defaultMemberRole(
                memberId = member.memberId, roleId = role.roleId
            )
            db.insert(
                MemberRoleEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(memberRole)
            )
            Timber.tag(TAG).i("CONGREGATION: Default Administrator imported")
            jsonLogger?.let { logger ->
                Timber.tag(TAG).i(
                    ": {\"member\": {%s}, \"memberMovement\": {%s}, \"memberRole\": {%s}}",
                    logger.encodeToString(member),
                    logger.encodeToString(memberMovement),
                    logger.encodeToString(memberRole)
                )
            }
            return member
        }

        // TERRITORIES:
        private fun insertDefTerritoryCategory(
            db: SupportSQLiteDatabase, territoryCategory: TerritoryCategoryEntity
        ): TerritoryCategoryEntity {
            db.insert(
                TerritoryCategoryEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(territoryCategory)
            )
            Timber.tag(TAG).i("TERRITORY: Default territory category imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(territoryCategory)) }
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
                    //isInPerimeter = num % 10 == 0,
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
                territories.add(territory)
            }
            Timber.tag(TAG).i("TERRITORIES: Default territories imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(
                    ": {\"territories\": {\"count\": %s, \"congregation\": {%s}}}",
                    territories.size, it.encodeToString(congregation)
                )
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
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(territoryMember)) }
            Timber.tag(TAG).i("TERRITORY: Default hand out territory imported")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(handOutTerritory)) }
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
            jsonLogger?.let {
                Timber.tag(TAG).i(": {%s}", it.encodeToString(deliveryTerritoryMember))
            }
            Timber.tag(TAG).i("TERRITORY: Delivery imported territory")
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(idleTerritory)) }
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
            jsonLogger?.let { Timber.tag(TAG).i(": {%s}", it.encodeToString(territoryStreet)) }
        }

        // TRANSFERS:
        private fun insertDefTransferObject(
            db: SupportSQLiteDatabase, transferObjectType: TransferObjectType
        ): TransferObjectEntity {
            val transferObject = when (transferObjectType) {
                TransferObjectType.ALL -> TransferObjectEntity.allTransferObject(ctx)
                TransferObjectType.MEMBERS -> TransferObjectEntity.membersTransferObject(ctx)
                TransferObjectType.TERRITORIES -> TransferObjectEntity.territoriesTransferObject(
                    ctx
                )

                TransferObjectType.BILLS -> TransferObjectEntity.billsTransferObject(ctx)
            }
            db.insert(
                TransferObjectEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(transferObject)
            )
            Timber.tag(TAG).i("Default transfer object imported")
            jsonLogger?.let {
                Timber.tag(TAG).i(": {\"transferObject\": {%s}}", it.encodeToString(transferObject))
            }
            return transferObject
        }

        private fun insertDefRoleTransferObject(
            db: SupportSQLiteDatabase, role: RoleEntity, transferObject: TransferObjectEntity,
            isPersonalData: Boolean
        ): RoleTransferObjectEntity {
            val roleTransferObject = RoleTransferObjectEntity.defaultRoleTransferObject(
                roleId = role.roleId, transferObjectId = transferObject.transferObjectId,
                isPersonalData = isPersonalData
            )
            db.insert(
                RoleTransferObjectEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE,
                Mapper.toContentValues(roleTransferObject)
            )
            Timber.tag(TAG).i("TRANSFERS: Default Role Transfer Object imported")
            jsonLogger?.let { logger ->
                Timber.tag(TAG).i(
                    ": {\"roleTransferObject\": {%s}}", logger.encodeToString(roleTransferObject)
                )
            }
            return roleTransferObject
        }
    }
}