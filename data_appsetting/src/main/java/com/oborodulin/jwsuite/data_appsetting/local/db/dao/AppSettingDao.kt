package com.oborodulin.jwsuite.data_appsetting.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oborodulin.jwsuite.data_appsetting.local.db.entities.AppSettingEntity
import com.oborodulin.jwsuite.domain.types.AppSettingParam
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.UUID

@Dao
interface AppSettingDao {
    // READS:
    @Query("SELECT * FROM ${AppSettingEntity.TABLE_NAME}")
    fun findAll(): Flow<List<AppSettingEntity>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${AppSettingEntity.TABLE_NAME} WHERE settingId = :id")
    fun findById(id: UUID): Flow<AppSettingEntity>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    @Query("SELECT paramValue FROM ${AppSettingEntity.TABLE_NAME} WHERE paramName LIKE '%' || :paramName || '%'")
    fun findByParamName(paramName: String): Flow<List<String>>

    @ExperimentalCoroutinesApi
    fun findDistinctByParamName(paramName: String) =
        findByParamName(paramName).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(setting: AppSettingEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg settings: AppSettingEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(settings: List<AppSettingEntity>)

    // UPDATES:
    @Update
    suspend fun update(setting: AppSettingEntity)

    @Update
    suspend fun update(vararg settings: AppSettingEntity)

    @Query("UPDATE ${AppSettingEntity.TABLE_NAME} SET paramValue = :paramValue WHERE paramName = :paramName")
    suspend fun updateByParamName(paramName: AppSettingParam, paramValue: String)

    // DELETES:
    @Delete
    suspend fun delete(settings: AppSettingEntity)

    @Delete
    suspend fun delete(vararg settings: AppSettingEntity)

    @Delete
    suspend fun delete(settings: List<AppSettingEntity>)

    @Query("DELETE FROM ${AppSettingEntity.TABLE_NAME} WHERE settingId = :id")
    suspend fun deleteById(id: UUID)

    @Query("DELETE FROM ${AppSettingEntity.TABLE_NAME}")
    suspend fun deleteAll()
}