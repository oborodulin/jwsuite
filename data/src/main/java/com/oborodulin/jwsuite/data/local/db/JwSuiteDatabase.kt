package com.oborodulin.jwsuite.data.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.oborodulin.home.common.BuildConfig
import com.oborodulin.home.common.util.LogLevel.LOG_DATABASE
import com.oborodulin.home.common.util.LogLevel.LOG_SECURE
import com.oborodulin.jwsuite.data.local.datastore.repositories.sources.LocalSessionManagerDataSource
import com.oborodulin.jwsuite.data.local.db.converters.JwSuiteTypeConverters
import com.oborodulin.jwsuite.data.local.db.dao.DatabaseDao
import com.oborodulin.jwsuite.data.local.db.dao.EventDao
import com.oborodulin.jwsuite.data.local.db.entities.EventEntity
import com.oborodulin.jwsuite.data.local.db.entities.MemberMinistryEntity
import com.oborodulin.jwsuite.data.local.db.populate.DaoPopulator
import com.oborodulin.jwsuite.data.local.db.populate.Populable
import com.oborodulin.jwsuite.data.util.Constants
import com.oborodulin.jwsuite.data.util.Constants.DATABASE_PASSPHRASE
import com.oborodulin.jwsuite.data_appsetting.local.db.dao.AppSettingDao
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.data_congregation.local.db.dao.CongregationDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.GroupDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.MemberDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.RoleDao
import com.oborodulin.jwsuite.data_congregation.local.db.dao.TransferDao
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.GroupEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberCongregationCrossRefEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberMovementEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.MemberRoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.role.RoleTransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.transfer.TransferObjectEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationTotalView
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.FavoriteCongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.GroupView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberActualRoleView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberLastCongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberLastMovementView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberMovementView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleTransferObjectView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberRoleView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberServiceRoleView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberView
import com.oborodulin.jwsuite.data_congregation.local.db.views.RoleTransferObjectView
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoCountryDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoLocalityDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoLocalityDistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoMicrodistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoRegionDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoRegionDistrictDao
import com.oborodulin.jwsuite.data_geo.local.db.dao.GeoStreetDao
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryTlEntity
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
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoCountryView
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
import com.oborodulin.jwsuite.data_geo.local.db.views.RegionView
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
import com.oborodulin.jwsuite.data_territory.local.db.views.CongregationTerritoryView
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
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryMemberView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryPrivateSectorView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportHouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportRoomView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryReportStreetView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetHouseView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetNamesAndHouseNumsView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryTotalView
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
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
        GeoCountryEntity::class, GeoCountryTlEntity::class, GeoRegionEntity::class, GeoRegionTlEntity::class,
        GeoRegionDistrictEntity::class, GeoRegionDistrictTlEntity::class,
        GeoLocalityEntity::class, GeoLocalityTlEntity::class,
        GeoLocalityDistrictEntity::class, GeoLocalityDistrictTlEntity::class,
        GeoMicrodistrictEntity::class, GeoMicrodistrictTlEntity::class,
        GeoStreetEntity::class, GeoStreetTlEntity::class, GeoStreetDistrictEntity::class,
        CongregationEntity::class, CongregationTotalEntity::class, GroupEntity::class, MemberEntity::class,
        MemberMovementEntity::class, MemberRoleEntity::class, MemberCongregationCrossRefEntity::class,
        MemberMinistryEntity::class, TerritoryCategoryEntity::class, TerritoryEntity::class,
        TerritoryStreetEntity::class, TransferObjectEntity::class, RoleTransferObjectEntity::class,
        TerritoryMemberCrossRefEntity::class, //TerritoryTotalEntity::class,
        HouseEntity::class, EntranceEntity::class, FloorEntity::class, RoomEntity::class,
        TerritoryMemberReportEntity::class,
        CongregationTerritoryCrossRefEntity::class,
        EventEntity::class],
    views = [
        GeoCountryView::class, RegionView::class, RegionDistrictView::class, LocalityView::class,
        LocalityDistrictView::class, MicrodistrictView::class, StreetView::class,
        GeoRegionView::class, GeoRegionDistrictView::class, GeoLocalityView::class,
        GeoLocalityDistrictView::class, GeoMicrodistrictView::class, GeoStreetView::class,
        CongregationView::class, FavoriteCongregationView::class, GroupView::class,
        MemberLastCongregationView::class, MemberLastMovementView::class,
        MemberView::class,
        MemberActualRoleView::class, MemberRoleView::class, MemberServiceRoleView::class,
        MemberMovementView::class, CongregationTotalView::class,
        CongregationTerritoryView::class, TerritoryMemberView::class,
        TerritoryMemberLastReceivingDateView::class, TerritoryPrivateSectorView::class,
        TerritoryView::class, TerritoryStreetView::class, TerritoryStreetHouseView::class,
        TerritoryStreetNamesAndHouseNumsView::class, TerritoryLocationView::class,
        TerritoriesHandOutView::class, TerritoriesAtWorkView::class, TerritoriesIdleView::class,
        HouseView::class, EntranceView::class, FloorView::class, RoomView::class,
        RoleTransferObjectView::class, MemberRoleTransferObjectView::class,
        TerritoryMemberReportView::class, TerritoryReportStreetView::class,
        TerritoryReportHouseView::class, TerritoryReportRoomView::class,
        TerritoryTotalView::class
    ],
    version = 1, exportSchema = true
)
@TypeConverters(JwSuiteTypeConverters::class)
abstract class JwSuiteDatabase : RoomDatabase() {
    // DAOs:
    abstract fun databaseDao(): DatabaseDao
    abstract fun appSettingDao(): AppSettingDao
    abstract fun eventDao(): EventDao

    // Geo:
    abstract fun geoCountryDao(): GeoCountryDao
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
    abstract fun roleDao(): RoleDao
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
                    if (LOG_SECURE) {
                        Timber.tag(TAG).d("databasePassphrase getting")
                    }
                    runBlocking {
                        // https://stackoverflow.com/questions/57088428/kotlin-flow-how-to-unsubscribe-stop
                        //.takeWhile { it.isNotEmpty() }.collect {= it}
                        databasePassphrase =
                            localSessionManagerDataSource.databasePassphrase().first()
                    }
                    if (LOG_SECURE) {
                        Timber.tag(TAG)
                            .d("databasePassphrase got: %s", databasePassphrase)
                    }
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
                        if (LOG_SECURE) {Timber.tag(TAG).d("databasePassphrase isNotEmpty")}
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
                    if (LOG_DATABASE) {
                        Timber.tag(TAG).d("Room database built")
                    }
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
        lateinit var prePopulator: Populable
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
                if (LOG_DATABASE) {
                    Timber.tag(TAG)
                        .d("onCreate -> CoroutineScope(Dispatchers.IO).async")
                }
                val database = getInstance(ctx, jsonLogger, localSessionManagerDataSource)
                if (LOG_DATABASE) {
                    Timber.tag(TAG).d(
                        "onCreate -> Start thread '%s': database.getInstance(...)",
                        Thread.currentThread().name
                    )
                }
                prePopulator = DaoPopulator(database, ctx, jsonLogger)
                //database.runInTransaction(Runnable {
                //if (LOG_DATABASE) {Timber.tag(TAG).d("onCreate -> database.runInTransaction -> run()")}
                //runBlocking {
                with(prePopulator) {
                    if (BuildConfig.DEBUG) {
                        prePopulateDb()
                    } else {
                        init()
                    }
                }
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
            if (LOG_DATABASE) {
                Timber.tag(TAG)
                    .d("Database onCreate(...) ended on thread '%s':", Thread.currentThread().name)
            }
        }
    }
}