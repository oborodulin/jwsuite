package com.oborodulin.jwsuite.data.local.db.repositories

import com.oborodulin.jwsuite.data.local.db.mappers.georegion.GeoRegionMappers
import com.oborodulin.jwsuite.data.local.db.repositories.sources.local.LocalGeoDataSource
import com.oborodulin.jwsuite.domain.model.Congregation
import com.oborodulin.jwsuite.domain.repositories.GeoRegionsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoRegionsRepositoryImpl @Inject constructor(
    private val localGeoDataSource: LocalGeoDataSource,
    private val mappers: GeoRegionMappers
) : GeoRegionsRepository {
    override fun getAll() = localGeoDataSource.getCongregations()
        .map(mappers.congregationViewListToCongregationListMapper::map)

    override fun get(congregationId: UUID) =
        localGeoDataSource.getCongregation(congregationId)
            .map(mappers.congregationViewToCongregationMapper::map)

    override fun getFavorite() =
        localGeoDataSource.getFavoriteCongregation()
            .map(mappers.congregationViewToCongregationMapper::map)

    override fun save(congregation: Congregation) = flow {
        if (congregation.id == null) {
            localGeoDataSource.insertCongregation(
                mappers.congregationToCongregationEntityMapper.map(
                    congregation
                )
            )
        } else {
            localGeoDataSource.updateCongregation(
                mappers.congregationToCongregationEntityMapper.map(
                    congregation
                )
            )
        }
        emit(congregation)
    }

    override fun delete(congregation: Congregation) = flow {
        localGeoDataSource.deleteCongregation(
            mappers.congregationToCongregationEntityMapper.map(
                congregation
            )
        )
        this.emit(congregation)
    }

    override fun deleteById(congregationId: UUID) = flow {
        localGeoDataSource.deleteCongregationById(congregationId)
        this.emit(congregationId)
    }

    override suspend fun deleteAll() = localGeoDataSource.deleteAllCongregations()

    override fun makeFavoriteById(congregationId: UUID) = flow {
        localGeoDataSource.makeFavoriteCongregationById(congregationId)
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