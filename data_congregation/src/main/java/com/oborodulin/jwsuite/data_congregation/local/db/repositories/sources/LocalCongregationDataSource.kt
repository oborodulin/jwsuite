package com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources

import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationTotalView
import com.oborodulin.jwsuite.data_congregation.local.db.views.CongregationView
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LocalCongregationDataSource {
    fun getCongregations(): Flow<List<CongregationView>>
    fun getCongregation(congregationId: UUID): Flow<CongregationView>
    fun getFavoriteCongregation(): Flow<CongregationView?>
    fun getFavoriteCongregationTotals(): Flow<CongregationTotalView?>
    suspend fun insertCongregation(congregation: CongregationEntity)
    suspend fun updateCongregation(congregation: CongregationEntity)
    suspend fun deleteCongregation(congregation: CongregationEntity)
    suspend fun deleteCongregationById(congregationId: UUID)
    suspend fun makeFavoriteCongregationById(congregationId: UUID)
    suspend fun deleteCongregations(congregations: List<CongregationEntity>)
    suspend fun deleteAllCongregations()
}