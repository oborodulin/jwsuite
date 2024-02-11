package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.geo.GeoLocalityDistrict
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoLocalityDistrictTlCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoLocalityDistrictsRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<GeoLocalityDistrict>>
    fun getAllByLocality(localityId: UUID): Flow<List<GeoLocalityDistrict>>
    fun getAllByStreet(streetId: UUID): Flow<List<GeoLocalityDistrict>>
    fun getAllForStreet(streetId: UUID): Flow<List<GeoLocalityDistrict>>
    fun get(localityDistrictId: UUID): Flow<GeoLocalityDistrict>
    fun save(localityDistrict: GeoLocalityDistrict): Flow<GeoLocalityDistrict>
    fun delete(localityDistrict: GeoLocalityDistrict): Flow<GeoLocalityDistrict>
    fun delete(localityDistrictId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractLocalityDistricts(): Flow<List<GeoLocalityDistrictCsv>>
    fun extractLocalityDistrictTls(): Flow<List<GeoLocalityDistrictTlCsv>>
    fun loadLocalityDistricts(localityDistricts: List<GeoLocalityDistrictCsv>): Flow<Int>
    fun loadLocalityDistrictTls(localityDistrictTls: List<GeoLocalityDistrictTlCsv>): Flow<Int>
}