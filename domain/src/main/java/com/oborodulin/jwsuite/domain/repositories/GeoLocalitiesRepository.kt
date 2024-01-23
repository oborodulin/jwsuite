package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.geo.GeoLocality
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityTlCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoLocalitiesRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<GeoLocality>>
    fun getAllByRegion(regionId: UUID): Flow<List<GeoLocality>>
    fun getAllByRegionDistrict(regionDistrictId: UUID): Flow<List<GeoLocality>>
    fun get(localityId: UUID): Flow<GeoLocality>
    fun save(locality: GeoLocality): Flow<GeoLocality>
    fun delete(locality: GeoLocality): Flow<GeoLocality>
    fun deleteById(localityId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractLocalities(): Flow<List<GeoLocalityCsv>>
    fun extractLocalityTls(): Flow<List<GeoLocalityTlCsv>>
    fun loadLocalities(localities: List<GeoLocalityCsv>): Flow<Int>
    fun loadLocalityTls(localityTls: List<GeoLocalityTlCsv>): Flow<Int>
}