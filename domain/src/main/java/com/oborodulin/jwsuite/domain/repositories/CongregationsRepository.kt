package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.congregation.Congregation
import com.oborodulin.jwsuite.domain.model.congregation.CongregationTotals
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationTotalCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface CongregationsRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<Congregation>>
    fun get(congregationId: UUID): Flow<Congregation>
    fun getFavorite(): Flow<Congregation?>
    fun getFavoriteTotals(): Flow<CongregationTotals?>
    fun save(congregation: Congregation): Flow<Congregation>
    fun delete(congregation: Congregation): Flow<Congregation>
    fun delete(congregationId: UUID): Flow<UUID>
    fun makeFavoriteById(congregationId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractCongregations(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<CongregationCsv>>

    fun extractCongregationTotals(
        username: String? = null, byFavorite: Boolean = false
    ): Flow<List<CongregationTotalCsv>>

    fun loadCongregations(congregations: List<CongregationCsv>): Flow<Int>
    fun loadCongregationTotals(congregationTotals: List<CongregationTotalCsv>): Flow<Int>
}