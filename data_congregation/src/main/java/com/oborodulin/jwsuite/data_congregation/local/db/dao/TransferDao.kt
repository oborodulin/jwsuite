package com.oborodulin.jwsuite.data_congregation.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oborodulin.jwsuite.data_congregation.local.db.entities.transfer.TransferObjectEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TransferDao {
    // EXTRACTS:
    @Query("SELECT * FROM ${TransferObjectEntity.TABLE_NAME}")
    fun findAllEntities(): Flow<List<TransferObjectEntity>>

    // READS:

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(transferObject: TransferObjectEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg transferObjects: TransferObjectEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(transferObjects: List<TransferObjectEntity>)

    // UPDATES:
    @Update
    suspend fun update(transferObject: TransferObjectEntity)

    @Update
    suspend fun update(vararg transferObjects: TransferObjectEntity)

    // DELETES:
    @Delete
    suspend fun delete(transferObject: TransferObjectEntity)

    @Delete
    suspend fun delete(vararg transferObjects: TransferObjectEntity)

    @Delete
    suspend fun delete(transferObjects: List<TransferObjectEntity>)

    @Query("DELETE FROM ${TransferObjectEntity.TABLE_NAME} WHERE transferObjectId = :transferObjectId")
    suspend fun deleteById(transferObjectId: UUID)

    @Query("DELETE FROM ${TransferObjectEntity.TABLE_NAME}")
    suspend fun deleteAll()
}