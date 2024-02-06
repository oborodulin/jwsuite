package com.oborodulin.jwsuite.data_congregation.local.db.repositories

import com.oborodulin.jwsuite.data_congregation.local.csv.mappers.congregation.CongregationCsvMappers
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationEntity
import com.oborodulin.jwsuite.data_congregation.local.db.entities.CongregationTotalEntity
import com.oborodulin.jwsuite.data_congregation.local.db.mappers.congregation.CongregationMappers
import com.oborodulin.jwsuite.data_congregation.local.db.repositories.sources.LocalCongregationDataSource
import com.oborodulin.jwsuite.domain.model.congregation.Congregation
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import com.oborodulin.jwsuite.domain.services.csv.CsvExtract
import com.oborodulin.jwsuite.domain.services.csv.CsvLoad
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationCsv
import com.oborodulin.jwsuite.domain.services.csv.model.congregation.CongregationTotalCsv
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

private const val TAG = "Data.CongregationsRepositoryImpl"

class CongregationsRepositoryImpl @Inject constructor(
    private val localCongregationDataSource: LocalCongregationDataSource,
    private val domainMappers: CongregationMappers,
    private val csvMappers: CongregationCsvMappers
) : CongregationsRepository {
    override fun getAll() = localCongregationDataSource.getCongregations()
        .map(domainMappers.congregationViewListToCongregationsListMapper::map)

    override fun get(congregationId: UUID) =
        localCongregationDataSource.getCongregation(congregationId)
            .map(domainMappers.congregationViewToCongregationMapper::map)

    override fun getFavorite() = localCongregationDataSource.getFavoriteCongregation()
        .map(domainMappers.congregationViewToCongregationMapper::nullableMap)

    override fun getFavoriteTotals() = localCongregationDataSource.getFavoriteCongregationTotals()
        .map(domainMappers.congregationTotalViewToCongregationTotalsMapper::nullableMap)

    override fun save(congregation: Congregation) = flow {
        if (congregation.id == null) {
            localCongregationDataSource.insertCongregation(
                domainMappers.congregationToCongregationEntityMapper.map(congregation)
            )
            Timber.tag(TAG).d("save(...) called: inserted congregation = %s", congregation)
        } else {
            localCongregationDataSource.updateCongregation(
                domainMappers.congregationToCongregationEntityMapper.map(congregation)
            )
            Timber.tag(TAG).d("save(...) called: updated congregation = %s", congregation)
        }
        emit(congregation)
        Timber.tag(TAG).d("save(...) emit congregation = %s", congregation)
    }

    override fun delete(congregation: Congregation) = flow {
        localCongregationDataSource.deleteCongregation(
            domainMappers.congregationToCongregationEntityMapper.map(congregation)
        )
        this.emit(congregation)
    }

    override fun deleteById(congregationId: UUID) = flow {
        localCongregationDataSource.deleteCongregationById(congregationId)
        this.emit(congregationId)
    }

    override suspend fun deleteAll() = localCongregationDataSource.deleteAllCongregations()

    override fun makeFavoriteById(congregationId: UUID) = flow {
        localCongregationDataSource.makeFavoriteCongregationById(congregationId)
        this.emit(congregationId)
    }

    /*
        fun nowPlaying(): Flow<PagingData<NetworkMovie>> {
            val config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5
            )
            return Pager(config) {
                AccountingDataSource(
                    nowPlayingUseCase = accountingUseCase
                )
            }.flow
        }
      */
    // -------------------------------------- CSV Transfer --------------------------------------
    @CsvExtract(fileNamePrefix = CongregationEntity.TABLE_NAME)
    override fun extractCongregations(username: String?, byFavorite: Boolean) =
        localCongregationDataSource.getCongregationEntities(username, byFavorite)
            .map(csvMappers.congregationEntityListToCongregationCsvListMapper::map)

    @CsvExtract(fileNamePrefix = CongregationTotalEntity.TABLE_NAME)
    override fun extractCongregationTotals(username: String?, byFavorite: Boolean) =
        localCongregationDataSource.getCongregationTotalEntities(username, byFavorite)
            .map(csvMappers.congregationTotalEntityListToCongregationTotalCsvListMapper::map)

    @CsvLoad<CongregationCsv>(
        fileNamePrefix = CongregationEntity.TABLE_NAME,
        contentType = CongregationCsv::class
    )
    override fun loadCongregations(congregations: List<CongregationCsv>) = flow {
        localCongregationDataSource.loadCongregationEntities(
            csvMappers.congregationCsvListToCongregationEntityListMapper.map(congregations)
        )
        emit(congregations.size)
    }

    @CsvLoad<CongregationTotalCsv>(
        fileNamePrefix = CongregationTotalEntity.TABLE_NAME,
        contentType = CongregationTotalCsv::class
    )
    override fun loadCongregationTotals(congregationTotals: List<CongregationTotalCsv>) = flow {
        localCongregationDataSource.loadCongregationTotalEntities(
            csvMappers.congregationTotalCsvListToCongregationTotalEntityListMapper.map(
                congregationTotals
            )
        )
        emit(congregationTotals.size)
    }
}