package com.oborodulin.jwsuite.data.local.db.repositories

import com.oborodulin.jwsuite.data.local.db.mappers.congregation.CongregationMappers
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalCongregationDataSource
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.domain.repositories.CongregationsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class CongregationsRepositoryImpl @Inject constructor(
    private val localCongregationDataSource: LocalCongregationDataSource,
    private val mappers: CongregationMappers
) : CongregationsRepository {
    override fun getAll() = localCongregationDataSource.getCongregations()
        .map(mappers.congregationViewListToCongregationListMapper::map)

    override fun get(congregationId: UUID) =
        localCongregationDataSource.getCongregation(congregationId).map(mappers.congregationViewToCongregationMapper::map)

    override fun getFavorite() =
        localCongregationDataSource.getFavoriteCongregation().map(mappers.congregationViewToCongregationMapper::map)

    override fun save(congregation: Congregation) = flow {
        if (congregation.id == null) {
            localCongregationDataSource.insertCongregation(mappers.congregationToCongregationEntityMapper.map(congregation))
        } else {
            localCongregationDataSource.updateCongregation(mappers.congregationToCongregationEntityMapper.map(congregation))
        }
        emit(congregation)
    }

    override fun delete(congregation: Congregation) = flow {
        localCongregationDataSource.deleteCongregation(mappers.congregationToCongregationEntityMapper.map(congregation))
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
}