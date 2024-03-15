package com.oborodulin.jwsuite.data_geo.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oborodulin.home.common.util.LogLevel.LOG_DATABASE
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoCountryView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.util.Locale
import java.util.UUID

private const val TAG = "Data.GeoCountryDao"

@Dao
interface GeoCountryDao {
    // EXTRACTS:
    @Query("SELECT * FROM ${GeoCountryEntity.TABLE_NAME}")
    fun selectEntities(): Flow<List<GeoCountryEntity>>

    @Query("SELECT * FROM ${GeoCountryTlEntity.TABLE_NAME}")
    fun selectTlEntities(): Flow<List<GeoCountryTlEntity>>

    // READS:
    @Query("SELECT * FROM ${GeoCountryView.VIEW_NAME} WHERE countryLocCode = :locale ORDER BY countryName")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoCountryView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GeoCountryView.VIEW_NAME} WHERE countryId = :countryId AND countryLocCode = :locale")
    fun findById(countryId: UUID, locale: String? = Locale.getDefault().language):
            Flow<GeoCountryView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${GeoCountryView.VIEW_NAME} WHERE upper(countryCode) = upper(substr(:locale, 1, 2)) AND countryLocCode = :locale")
    fun findByDefaultLocale(locale: String? = Locale.getDefault().language): Flow<GeoCountryView?>

    @ExperimentalCoroutinesApi
    fun findDistinctByDefaultLocale() = findByDefaultLocale().distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(country: GeoCountryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg countries: GeoCountryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(countries: List<GeoCountryEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTls(countryTls: List<GeoCountryTlEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg textContent: GeoCountryTlEntity)

    @Transaction
    suspend fun insert(country: GeoCountryEntity, textContent: GeoCountryTlEntity) {
        insert(country)
        insert(textContent)
    }

    // UPDATES:
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(country: GeoCountryEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg countries: GeoCountryEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg textContent: GeoCountryTlEntity)

    @Transaction
    suspend fun update(country: GeoCountryEntity, textContent: GeoCountryTlEntity) {
        if (LOG_DATABASE) {
            Timber.tag(TAG).d("country = %s; textContent = %s", country, textContent)
        }
        update(country)
        update(textContent)
        val updatedCountry = findById(country.countryId).first()
        if (LOG_DATABASE) {
            Timber.tag(TAG).d("updatedCountry = %s", updatedCountry)
        }
    }

    // DELETES:
    @Delete
    suspend fun delete(country: GeoCountryEntity)

    @Delete
    suspend fun delete(vararg countries: GeoCountryEntity)

    @Delete
    suspend fun delete(countries: List<GeoCountryEntity>)

    @Query("DELETE FROM ${GeoCountryEntity.TABLE_NAME} WHERE countryId = :countryId")
    suspend fun deleteById(countryId: UUID)

    @Query("DELETE FROM ${GeoCountryEntity.TABLE_NAME}")
    suspend fun deleteAll()
}