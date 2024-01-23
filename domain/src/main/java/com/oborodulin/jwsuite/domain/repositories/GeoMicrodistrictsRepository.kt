package com.oborodulin.jwsuite.domain.repositories

import com.oborodulin.jwsuite.domain.model.geo.GeoMicrodistrict
import com.oborodulin.jwsuite.domain.services.csv.CsvTransferableRepo
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictCsv
import com.oborodulin.jwsuite.domain.services.csv.model.geo.GeoMicrodistrictTlCsv
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface GeoMicrodistrictsRepository : CsvTransferableRepo {
    fun getAll(): Flow<List<GeoMicrodistrict>>
    fun getAllByLocality(localityId: UUID): Flow<List<GeoMicrodistrict>>
    fun getAllByLocalityDistrict(localityDistrictId: UUID): Flow<List<GeoMicrodistrict>>
    fun getAllByStreet(streetId: UUID): Flow<List<GeoMicrodistrict>>
    fun getAllForStreet(streetId: UUID): Flow<List<GeoMicrodistrict>>
    fun get(microdistrictId: UUID): Flow<GeoMicrodistrict>
    fun save(microdistrict: GeoMicrodistrict): Flow<GeoMicrodistrict>
    fun delete(microdistrict: GeoMicrodistrict): Flow<GeoMicrodistrict>
    fun deleteById(microdistrictId: UUID): Flow<UUID>
    suspend fun deleteAll()

    // -------------------------------------- CSV Transfer --------------------------------------
    fun extractMicrodistricts(): Flow<List<GeoMicrodistrictCsv>>
    fun extractMicrodistrictTls(): Flow<List<GeoMicrodistrictTlCsv>>
    fun loadMicrodistricts(microdistricts: List<GeoMicrodistrictCsv>): Flow<Int>
    fun loadMicrodistrictTls(microdistrictTls: List<GeoMicrodistrictTlCsv>): Flow<Int>
}