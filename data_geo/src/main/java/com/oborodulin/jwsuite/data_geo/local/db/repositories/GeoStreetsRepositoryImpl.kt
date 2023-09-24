package com.oborodulin.jwsuite.data_geo.local.db.repositories

import com.oborodulin.jwsuite.data_geo.local.db.mappers.geostreet.GeoStreetMappers
import com.oborodulin.jwsuite.data_geo.local.db.repositories.sources.local.LocalGeoStreetDataSource
import com.oborodulin.jwsuite.domain.model.GeoStreet
import com.oborodulin.jwsuite.domain.repositories.GeoStreetsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class GeoStreetsRepositoryImpl @Inject constructor(
    private val localStreetDataSource: LocalGeoStreetDataSource,
    private val mappers: GeoStreetMappers
) : GeoStreetsRepository {
    override fun getAll() = localStreetDataSource.getAllStreets()
        .map(mappers.geoStreetViewListToGeoStreetsListMapper::map)

    override fun getAllByLocality(localityId: UUID, isPrivateSector: Boolean?) =
        localStreetDataSource.getLocalityStreets(localityId, isPrivateSector)
            .map(mappers.geoStreetViewListToGeoStreetsListMapper::map)

    override fun getAllByLocalityDistrict(localityDistrictId: UUID, isPrivateSector: Boolean?) =
        localStreetDataSource.getLocalityDistrictStreets(localityDistrictId, isPrivateSector)
            .map(mappers.geoStreetViewListToGeoStreetsListMapper::map)

    override fun getAllByMicrodistrict(microdistrictId: UUID, isPrivateSector: Boolean?) =
        localStreetDataSource.getMicrodistrictStreets(microdistrictId, isPrivateSector)
            .map(mappers.geoStreetViewListToGeoStreetsListMapper::map)

    override fun getAllForTerritory(
        localityId: UUID, localityDistrictId: UUID?, microdistrictId: UUID?, excludes: List<UUID>
    ) = localStreetDataSource.getStreetsForTerritory(
        localityId, localityDistrictId, microdistrictId, excludes
    ).map(mappers.geoStreetViewListToGeoStreetsListMapper::map)

    override fun get(streetId: UUID) = localStreetDataSource.getStreet(streetId)
        .map(mappers.geoStreetViewToGeoStreetMapper::map)

    override fun save(street: GeoStreet) = flow {
        if (street.id == null) {
            localStreetDataSource.insertStreet(
                mappers.geoStreetToGeoStreetEntityMapper.map(street),
                mappers.geoStreetToGeoStreetTlEntityMapper.map(street)
            )
        } else {
            localStreetDataSource.updateStreet(
                mappers.geoStreetToGeoStreetEntityMapper.map(street),
                mappers.geoStreetToGeoStreetTlEntityMapper.map(street)
            )
        }
        emit(street)
    }

    override fun delete(street: GeoStreet) = flow {
        localStreetDataSource.deleteStreet(
            mappers.geoStreetToGeoStreetEntityMapper.map(street)
        )
        this.emit(street)
    }

    override fun deleteById(streetId: UUID) = flow {
        localStreetDataSource.deleteStreetById(streetId)
        this.emit(streetId)
    }

    override suspend fun deleteAll() = localStreetDataSource.deleteAllStreets()

    // Districts:
    override suspend fun deleteStreetDistrict(streetDistrictId: UUID) =
        localStreetDataSource.deleteStreetDistrict(streetDistrictId)

    // Locality Districts:
    override suspend fun insertStreetLocalityDistricts(
        streetId: UUID, localityDistrictIds: List<UUID>
    ) = flow {
        localityDistrictIds.forEach {
            localStreetDataSource.insertStreetLocalityDistrict(streetId, it)
        }
        this.emit(localityDistrictIds)
    }

    override suspend fun deleteLocalityDistrict(streetId: UUID, localityDistrictId: UUID) =
        localStreetDataSource.deleteStreetLocalityDistrict(streetId, localityDistrictId)

    // Microdistricts:
    override suspend fun insertStreetMicrodistricts(
        streetId: UUID, districtIds: Map<UUID, UUID>
    ) = flow {
        val microdistrictIds = mutableListOf<UUID>()
        districtIds.forEach { (localityDistrictId, microdistrictId) ->
            localStreetDataSource.insertStreetMicrodistrict(
                streetId, localityDistrictId, microdistrictId
            )
            microdistrictIds.add(microdistrictId)
        }
        this.emit(microdistrictIds)
    }

    override suspend fun deleteMicrodistrict(streetId: UUID, microdistrictId: UUID) =
        localStreetDataSource.deleteStreetLocalityDistrict(streetId, microdistrictId)

}