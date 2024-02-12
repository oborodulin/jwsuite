package com.oborodulin.jwsuite.data_congregation.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.pojo.CongregationWithGroupMembers
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationTotalView
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationView
import com.oborodulin.jwsuite.data_congregation.local.db.views.MemberLastCongregationView
import com.oborodulin.jwsuite.domain.util.Constants.DB_FALSE
import com.oborodulin.jwsuite.domain.util.Constants.DB_TRUE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import timber.log.Timber
import java.time.OffsetDateTime
import java.util.UUID

private const val TAG = "Data.CongregationDao"

@Dao
interface CongregationDao {
    // EXTRACTS:
    @Query(
        """
    SELECT c.* FROM ${CongregationEntity.TABLE_NAME} c LEFT JOIN ${MemberLastCongregationView.VIEW_NAME} mcv
        ON c.congregationId = mcv.mcCongregationsId AND mcv.pseudonym = :username
    WHERE (:username IS NULL OR mcv.mcCongregationsId IS NOT NULL) AND c.isFavorite = (CASE WHEN :byFavorite = $DB_TRUE THEN $DB_TRUE ELSE c.isFavorite END)
    """
    )
    fun findEntitiesByUsernameAndFavoriteMark(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<CongregationEntity>>

    @Query(
        """
    SELECT ct.* FROM ${CongregationTotalEntity.TABLE_NAME} ct JOIN ${CongregationEntity.TABLE_NAME} c
            ON ct.ctlCongregationsId = c.congregationId AND c.isFavorite = (CASE WHEN :byFavorite = $DB_TRUE THEN $DB_TRUE ELSE c.isFavorite END)
        LEFT JOIN ${MemberLastCongregationView.VIEW_NAME} mcv ON ct.ctlCongregationsId = mcv.mcCongregationsId AND mcv.pseudonym = :username
    WHERE (:username IS NULL OR mcv.mcCongregationsId IS NOT NULL)
    """
    )
    fun findTotalEntitiesByUsernameAndFavoriteMark(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<CongregationTotalEntity>>

    // READS:
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
    fun findFavoriteCongregation(): Flow<CongregationView?>

    //fun findFavorite() = flow { emit(findFavoriteCongregation()) }

    @ExperimentalCoroutinesApi
    fun findDistinctFavorite() = findFavoriteCongregation().distinctUntilChanged()

    //-----------------------------
    @Transaction
    @Query("SELECT * FROM ${CongregationEntity.TABLE_NAME} ORDER BY isFavorite DESC")
    fun findCongregationWithGroupMembers(): Flow<List<CongregationWithGroupMembers>>

    //-----------------------------
    @Query("SELECT * FROM ${CongregationTotalView.VIEW_NAME}")
    fun findTotals(): Flow<CongregationTotalView?>

    @ExperimentalCoroutinesApi
    fun findDistinctTotals() = findTotals().distinctUntilChanged()

    //-----------------------------
    @Query("SELECT * FROM ${CongregationTotalEntity.TABLE_NAME} WHERE ctlCongregationsId = :congregationId AND ifnull(lastVisitDate, '') = ifnull(:lastVisitDate, '')")
    fun findTotalByCongregationId(
        congregationId: UUID, lastVisitDate: OffsetDateTime? = null
    ): Flow<CongregationTotalEntity?>

    @ExperimentalCoroutinesApi
    fun findDistinctTotalByCongregationId(
        congregationId: UUID, lastVisitDate: OffsetDateTime? = null
    ) = findTotalByCongregationId(congregationId, lastVisitDate).distinctUntilChanged()

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(congregation: CongregationEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg congregations: CongregationEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(congregations: List<CongregationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(congregationTotal: CongregationTotalEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTotals(congregationTotals: List<CongregationTotalEntity>)

    // UPDATES:
    @Update
    suspend fun update(congregation: CongregationEntity)

    @Update
    suspend fun update(vararg congregations: CongregationEntity)

    @Update
    suspend fun update(congregationTotal: CongregationTotalEntity)

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
    suspend fun insertWithFavoriteAndTotals(congregation: CongregationEntity) {
        insert(congregation)
        if (congregation.isFavorite) {
            clearFavoritesById(congregation.congregationId)
        }
        insert(
            CongregationTotalEntity(
                lastVisitDate = congregation.lastVisitDate,
                ctlCongregationsId = congregation.congregationId
            )
        )
        congregation.lastVisitDate?.let {
            insert(CongregationTotalEntity(ctlCongregationsId = congregation.congregationId))
        }
    }

    @Transaction
    suspend fun updateWithFavoriteAndTotals(congregation: CongregationEntity) {
        Timber.tag(TAG)
            .d("updateWithFavoriteAndTotals(...) called: congregation = %s", congregation)
        if (congregation.isFavorite) {
            clearFavoritesById(congregation.congregationId)
            update(congregation)
        } else {
            var updatedCongregation = congregation
            findFavoriteCongregation().first()?.let { favorite ->
                Timber.tag(TAG).d("findFavoriteCongregation() called: favorite = %s", favorite)
                if (favorite.congregation.congregationId == congregation.congregationId) {
                    updatedCongregation = congregation.copy(isFavorite = true)
                }
            }
            update(updatedCongregation)
            Timber.tag(TAG)
                .d("updateWithFavoriteAndTotals: updatedCongregation = %s", updatedCongregation)
        }
        congregation.lastVisitDate?.let { lastVisitDate ->
            if (findTotalByCongregationId(
                    congregation.congregationId,
                    lastVisitDate
                ).first() == null
            ) {
                findTotalByCongregationId(congregation.congregationId).first()?.let {
                    update(it.copy(lastVisitDate = lastVisitDate))
                    insert(CongregationTotalEntity(ctlCongregationsId = congregation.congregationId))
                }
            }
        }
        Timber.tag(TAG).d("updateWithFavoriteAndTotals(...) ending")
    }
}