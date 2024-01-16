package com.oborodulin.jwsuite.data_congregation.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.pojo.CongregationWithGroupMembers
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationTotalView
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationView
import com.oborodulin.jwsuite.domain.util.Constants.DB_FALSE
import com.oborodulin.jwsuite.domain.util.Constants.DB_TRUE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.util.UUID

private const val TAG = "Data.CongregationDao"

@Dao
interface CongregationDao {
    // READS:
    @Query("SELECT * FROM ${CongregationEntity.TABLE_NAME}")
    fun selectAll(): Flow<List<CongregationEntity>>

    @Query("SELECT * FROM ${CongregationView.VIEW_NAME} ORDER BY isFavorite DESC")
    fun findAll(): Flow<List<CongregationView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${CongregationView.VIEW_NAME} WHERE congregationId = :congregationId")
    fun findById(congregationId: UUID): Flow<CongregationView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${CongregationEntity.TABLE_NAME} WHERE congregationNum = :congregationNum LIMIT 1")
    fun findByCongregationNum(congregationNum: String): Flow<CongregationEntity?>

    @Query("SELECT EXISTS (SELECT * FROM ${CongregationEntity.TABLE_NAME} WHERE congregationNum = :congregationNum LIMIT 1)")
    fun existsByCongregationNum(congregationNum: String): Boolean

    @Query("SELECT * FROM ${CongregationView.VIEW_NAME} WHERE congregationName LIKE '%' || :congregationName || '%'")
    fun findByCongregationName(congregationName: String): Flow<List<CongregationView>>

    @Query("SELECT * FROM ${CongregationView.VIEW_NAME} WHERE isFavorite = $DB_TRUE")
    fun findFavoriteCongregation(): CongregationView?

    fun findFavorite() = flow { emit(findFavoriteCongregation()) }

    @ExperimentalCoroutinesApi
    fun findDistinctFavorite() = findFavorite().distinctUntilChanged()

    //-----------------------------
    @Transaction
    @Query("SELECT * FROM ${CongregationEntity.TABLE_NAME} ORDER BY isFavorite DESC")
    fun findCongregationWithGroupMembers(): Flow<List<CongregationWithGroupMembers>>

    //-----------------------------
    @Query("SELECT * FROM ${CongregationTotalView.VIEW_NAME}")
    fun findTotals(): Flow<CongregationTotalView?>

    @ExperimentalCoroutinesApi
    fun findDistinctfindTotals() = findTotals().distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(congregation: CongregationEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg congregations: CongregationEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(congregations: List<CongregationEntity>)

    // UPDATES:
    @Update
    suspend fun update(congregation: CongregationEntity)

    @Update
    suspend fun update(vararg congregations: CongregationEntity)

    // DELETES:
    @Delete
    suspend fun delete(congregation: CongregationEntity)

    @Delete
    suspend fun delete(vararg congregations: CongregationEntity)

    @Delete
    suspend fun delete(congregations: List<CongregationEntity>)

    @Query("DELETE FROM ${CongregationEntity.TABLE_NAME} WHERE congregationId = :congregationId")
    suspend fun deleteById(congregationId: UUID)

    @Query("DELETE FROM ${CongregationEntity.TABLE_NAME}")
    suspend fun deleteAll()

    // API:
    @Query("UPDATE ${CongregationEntity.TABLE_NAME} SET isFavorite = $DB_TRUE WHERE congregationId = :congregationId AND isFavorite = $DB_FALSE")
    suspend fun setFavoriteById(congregationId: UUID)

    @Query("UPDATE ${CongregationEntity.TABLE_NAME} SET isFavorite = $DB_FALSE WHERE congregationId <> :congregationId AND isFavorite = $DB_TRUE")
    suspend fun clearFavoritesById(congregationId: UUID)

    @Transaction
    suspend fun makeFavoriteById(congregationId: UUID) {
        clearFavoritesById(congregationId)
        setFavoriteById(congregationId)
    }

    @Transaction
    suspend fun insertWithFavorite(congregation: CongregationEntity) {
        insert(congregation)
        if (congregation.isFavorite) {
            clearFavoritesById(congregation.congregationId)
        }
    }

    @Transaction
    suspend fun updateWithFavorite(congregation: CongregationEntity) {
        Timber.tag(TAG).d("updateWithFavorite(...) called: congregation = %s", congregation)
        if (congregation.isFavorite) {
            clearFavoritesById(congregation.congregationId)
            update(congregation)
        } else {
            var updatedCongregation = congregation
            findFavoriteCongregation()?.let { favorite ->
                Timber.tag(TAG).d("findFavorite() called: favorite = %s", favorite)
                if (favorite.congregation.congregationId == congregation.congregationId) {
                    updatedCongregation = congregation.copy(isFavorite = true)
                }
            }
            update(updatedCongregation)
            Timber.tag(TAG)
                .d("updateWithFavorite: updatedCongregation = %s", updatedCongregation)
        }
        Timber.tag(TAG).d("updateWithFavorite(...) ending")
    }
}