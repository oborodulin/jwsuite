package com.oborodulin.jwsuite.data_geo.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryEntity
import com.oborodulin.jwsuite.data_geo.local.db.entities.GeoCountryTlEntity
import com.oborodulin.jwsuite.data_geo.local.db.views.GeoCountryView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import java.util.UUID

@Dao
interface GeoCountryDao {
    // READS:
    @Query("SELECT * FROM ${GeoCountryEntity.TABLE_NAME}")
    fun selectEntities(): Flow<List<GeoCountryEntity>>

    @Query("SELECT * FROM ${GeoCountryTlEntity.TABLE_NAME}")
    fun selectTlEntities(): Flow<List<GeoCountryTlEntity>>

    @Query("SELECT * FROM ${GeoCountryView.VIEW_NAME} WHERE countryLocCode = :locale ORDER BY countryName")
    fun findAll(locale: String? = Locale.getDefault().language): Flow<List<GeoCountryView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${GeoCountryView.VIEW_NAME} WHERE countryId = :countryId AND countryLocCode = :locale")
    fun findById(countryId: UUID, locale: String? = Locale.getDefault().language):
            Flow<GeoCountryView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

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
    @Update
    suspend fun update(country: GeoCountryEntity)

    @Update
    suspend fun update(vararg countries: GeoCountryEntity)

    @Update
    suspend fun update(vararg textContent: GeoCountryTlEntity)

    @Transaction
    suspend fun update(country: GeoCountryEntity, textContent: GeoCountryTlEntity) {
        update(country)
        update(textContent)
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