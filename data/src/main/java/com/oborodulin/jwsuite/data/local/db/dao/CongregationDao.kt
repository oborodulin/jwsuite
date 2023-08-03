package com.oborodulin.jwsuite.data.local.db.dao

import androidx.room.*
import com.oborodulin.jwsuite.data.local.db.entities.*
import com.oborodulin.jwsuite.data.local.db.entities.pojo.CongregationWithGroupMembers
import com.oborodulin.jwsuite.data.local.db.views.CongregationView
import com.oborodulin.jwsuite.data.util.Constants.DB_FALSE
import com.oborodulin.jwsuite.data.util.Constants.DB_TRUE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.time.OffsetDateTime
import java.util.*

@Dao
interface CongregationDao {
    // READS:
    @Query("SELECT * FROM ${CongregationView.VIEW_NAME} ORDER BY isFavorite DESC")
    fun findAll(): Flow<List<CongregationView>>

    @ExperimentalCoroutinesApi
    fun findDistinctAll() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM ${CongregationView.VIEW_NAME} WHERE congregationId = :congregationId")
    fun findById(congregationId: UUID): Flow<CongregationView>

    @ExperimentalCoroutinesApi
    fun findDistinctById(id: UUID) = findById(id).distinctUntilChanged()

    @Query("SELECT * FROM ${CongregationEntity.TABLE_NAME} WHERE congregationNum = :congregationNum LIMIT 1")
    fun findByCongregationNum(congregationNum: String): Flow<CongregationEntity?>

    @Query("SELECT EXISTS (SELECT * FROM ${CongregationEntity.TABLE_NAME} WHERE congregationNum = :congregationNum LIMIT 1)")
    fun existsByCongregationNum(congregationNum: String): Boolean

    @Query("SELECT * FROM ${CongregationView.VIEW_NAME} WHERE congregationName LIKE '%' || :congregationName || '%'")
    fun findByCongregationName(congregationName: String): Flow<List<CongregationView>>

    @Query("SELECT * FROM ${CongregationView.VIEW_NAME} WHERE isFavorite = $DB_TRUE")
    fun findFavorite(): Flow<CongregationView?>

    @ExperimentalCoroutinesApi
    fun findDistinctFavorite() = findFavorite().distinctUntilChanged()

    @Transaction
    @Query("SELECT * FROM ${CongregationEntity.TABLE_NAME} ORDER BY isFavorite DESC")
    fun findCongregationWithGroupMembers(): Flow<List<CongregationWithGroupMembers>>

    // INSERTS:
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(congregation: CongregationEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg congregations: CongregationEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(congregations: List<CongregationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg congregationMember: CongregationMemberCrossRefEntity)

    suspend fun insert(
        congregation: CongregationEntity, member: MemberEntity,
        activityDate: OffsetDateTime = OffsetDateTime.now()
    ) = insert(
        CongregationMemberCrossRefEntity(
            cmCongregationsId = congregation.congregationId,
            cmMembersId = member.memberId,
            activityDate = activityDate
        )
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg congregationTerritory: CongregationTerritoryCrossRefEntity)

    suspend fun insert(
        congregation: CongregationEntity, territory: TerritoryEntity,
        startUsingDate: OffsetDateTime = OffsetDateTime.now()
    ) = insert(
        CongregationTerritoryCrossRefEntity(
            ctCongregationsId = congregation.congregationId,
            ctTerritoriesId = territory.territoryId,
            startUsingDate = startUsingDate
        )
    )

    // UPDATES:
    @Update
    suspend fun update(congregation: CongregationEntity)

    @Update
    suspend fun update(vararg congregations: CongregationEntity)

    @Update
    suspend fun update(vararg congregationMember: CongregationMemberCrossRefEntity)

    @Update
    suspend fun update(vararg congregationTerritory: CongregationTerritoryCrossRefEntity)

    // DELETES:
    @Delete
    suspend fun delete(congregation: CongregationEntity)

    @Delete
    suspend fun delete(vararg congregations: CongregationEntity)

    @Delete
    suspend fun delete(congregations: List<CongregationEntity>)

    @Query("DELETE FROM ${CongregationEntity.TABLE_NAME} WHERE congregationId = :congregationId")
    suspend fun deleteById(congregationId: UUID)

    @Delete
    suspend fun deleteMember(vararg congregationMember: CongregationMemberCrossRefEntity)

    @Query("DELETE FROM ${CongregationMemberCrossRefEntity.TABLE_NAME} WHERE congregationMemberId = :congregationMemberId")
    suspend fun deleteMemberById(congregationMemberId: UUID)

    @Query("DELETE FROM ${CongregationMemberCrossRefEntity.TABLE_NAME} WHERE cmCongregationsId = :congregationId")
    suspend fun deleteMembersByCongregationId(congregationId: UUID)

    @Delete
    suspend fun deleteTerritory(vararg congregationTerritory: CongregationTerritoryCrossRefEntity)

    @Query("DELETE FROM ${CongregationTerritoryCrossRefEntity.TABLE_NAME} WHERE congregationTerritoryId = :congregationTerritoryId")
    suspend fun deleteTerritoryById(congregationTerritoryId: UUID)

    @Query("DELETE FROM ${CongregationTerritoryCrossRefEntity.TABLE_NAME} WHERE ctCongregationsId = :congregationId")
    suspend fun deleteTerritoriesByCongregationId(congregationId: UUID)

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
        var updatedCongregation = congregation
        if (congregation.isFavorite) {
            clearFavoritesById(congregation.congregationId)
        } else {
            findFavorite().collect {favorite ->
                favorite?.let {
                    if (it.congregation.congregationId == congregation.congregationId) {
                        updatedCongregation = congregation.copy(isFavorite = true)
                    }
                }
            }
        }
        update(updatedCongregation)
    }
}